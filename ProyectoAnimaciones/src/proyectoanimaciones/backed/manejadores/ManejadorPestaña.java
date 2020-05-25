/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package proyectoanimaciones.backed.manejadores;

import java.awt.*;
import java.awt.event.*;
import java.awt.image.*;
import java.io.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.*;
import javax.swing.*;
import org.netbeans.lib.awtextra.*;
import proyectoanimaciones.backed.objetos.*;
import proyectoanimaciones.backed.objetos.Dimension;
import proyectoanimaciones.backed.objetos.Fondo;
import proyectoanimaciones.backed.objetos.Green;
import proyectoanimaciones.backed.objetos.Red;
import proyectoanimaciones.gui.DialogEditorGrafico;

/**
 *
 * @author bryan
 */
public class ManejadorPestaña {
    
    private static ManejadorPestaña manejadorPestañas;
    private  ManejadorExportar me = ManejadorExportar.getInstancia();
    private DialogEditorGrafico dialogEditorGrafico;
    
    private ManejadorPestaña(){}

    public static ManejadorPestaña getInstancia() {
        if (manejadorPestañas == null) {
            manejadorPestañas = new ManejadorPestaña();
        }
        return manejadorPestañas;
    }
    
    public void repintar(JLabel [][] labels, int filas, int columnas, java.awt.Color fondo){
        for (int i = 0; i < filas; i++) {
            for (int j = 0; j < columnas; j++) {
                labels[i][j].setBackground(fondo);
            }
        }
    }
    
    public void pintar(JLabel [][] labels, java.util.List<Pintar> pintar){
        pintar.forEach((pint) -> {
            if (pint.getDimesionX().isRango() && pint.getDimesionY().isRango()) {
                for (int i = pint.getDimesionX().getRangoInicial(); i < pint.getDimesionX().getRangoFinal(); i++) {
                    for (int j = pint.getDimesionY().getRangoInicial(); j < pint.getDimesionY().getRangoFinal(); j++) {
                        labels[i][j].setBackground(pint.getIdColor().getColor());
                    }
                }
            } else if (pint.getDimesionX().isRango()){
                for (int i = pint.getDimesionX().getRangoInicial(); i < pint.getDimesionX().getRangoFinal(); i++) {
                    labels[i][pint.getDimesionY().getDimension()].setBackground(pint.getIdColor().getColor());
                }
            } else if (pint.getDimesionY().isRango()) {
                for (int i = pint.getDimesionY().getRangoInicial(); i < pint.getDimesionY().getRangoFinal(); i++) {
                    labels[pint.getDimesionX().getDimension()][i].setBackground(pint.getIdColor().getColor());
                }
            } else {
                labels[pint.getDimesionX().getDimension()][pint.getDimesionY().getDimension()].setBackground(pint.getIdColor().getColor());
            }
        });
    }
    
    public proyectoanimaciones.backed.objetos.Color search(Lienzo lienzo, java.awt.Color color){ 
        for (proyectoanimaciones.backed.objetos.Color c : lienzo.getColores()) {
            if (c.getFondo().getColor().getRGB() == color.getRGB()) {
                return c;
            }
        } return null;
    }
    
    public Duracion searchDuracion(Lienzo lienzo, String id){
        for (Duracion img : lienzo.getTiempo().getImagenes()) {
            if (img.getId().equals(id)) {
                return img;
            }
        } return null;
    }
    
    public void comprobarColores(Lienzo lienzo, JLabel[][] comparar, String id){
        lienzo.cleanPint(id);
        Duracion aux = searchDuracion(lienzo, id);
        for (int i = 0; i < lienzo.getTamaño().getDimensionX(); i++) {
            for (int j = 0; j < lienzo.getTamaño().getDimensionY(); j++) {
                if (lienzo.getFondo().getColor().getRGB() != comparar[i][j].getBackground().getRGB()) {
                    lienzo.getPintar().add(new Pintar(search(lienzo, comparar[i][j].getBackground()), aux, new Dimension(i), new Dimension(j)));
                }
            }
        }
    }
    
    public void addNuevaPestaña(JTabbedPane pestaña, String tituloPestaña, Lienzo lienzo, DialogEditorGrafico deg){
        dialogEditorGrafico = deg;
        int filas = lienzo.getTamaño().getDimensionX(), columnas = lienzo.getTamaño().getDimensionY();    
        
        int contadorAncho = 0, contadorAlto = 0;
        Duracion idActual = new Duracion(lienzo.getTiempo().getInicio(), 0);
        
        Dimension pixels = new Dimension(lienzo.getTamaño().getCuadrado());
        Dimension x = new Dimension(lienzo.getTamaño().getDimensionX());
        Dimension y = new Dimension(lienzo.getTamaño().getDimensionY());
        
        JLabel coloresEnPaleta[] = new JLabel[lienzo.getColores().size()];
        JLabel lblCantidadImagenes = new javax.swing.JLabel();
        JLabel lblInicio = new javax.swing.JLabel();
        JLabel lblFin = new javax.swing.JLabel();
        JLabel lblImagen = new javax.swing.JLabel();
        JLabel lblDuracion = new javax.swing.JLabel();
        JLabel lblColores = new javax.swing.JLabel();
        JLabel lblColorSeleccionado = new javax.swing.JLabel();
        JLabel jtf[][] = new JLabel[filas][columnas];

        JPanel pestañaNueva = new javax.swing.JPanel();
        JPanel panelPrincipalPestaña = new javax.swing.JPanel();
        JPanel panelHerramientas = new javax.swing.JPanel();
        JPanel panelAddImagen = new javax.swing.JPanel();
        JPanel panelColores = new javax.swing.JPanel();

        JTextField txtDuraciones = new javax.swing.JTextField();
        JTextField txtCantidad = new javax.swing.JTextField();
        JTextField txtSeleccionado = new javax.swing.JTextField();

        JComboBox comboBoxListaInicio = new javax.swing.JComboBox<>();
        JComboBox comboBoxListaFin = new javax.swing.JComboBox<>();
        JComboBox comboBoxListaImagenes = new javax.swing.JComboBox<>();

        JCheckBox checkBoxBorrador = new javax.swing.JCheckBox();

        JScrollPane panelScrollColores = new javax.swing.JScrollPane();
        JScrollPane panelScrollImagen = new javax.swing.JScrollPane();

        JTextField txtFiels[] = new JTextField[lienzo.getColores().size()];
        JButton btnGenerar = new JButton("Generar");
        
        lblColorSeleccionado.setText("Selecionado");
        lblCantidadImagenes.setText("Cantidad:");
        lblInicio.setText("Inicio:");
        lblFin.setText("Fin:");
        lblImagen.setText("Imagen:");
        lblDuracion.setText("Duración");
        lblColores.setText("COLORES");

        txtDuraciones.addKeyListener(new java.awt.event.KeyAdapter() {
            @Override
            public void keyTyped(java.awt.event.KeyEvent evt) {
                char value = evt.getKeyChar();
                if (value <'0' || value >'9') evt.consume();
            }
        }); 
        
        txtCantidad.addKeyListener(new java.awt.event.KeyAdapter() {
            @Override
            public void keyTyped(java.awt.event.KeyEvent evt) {
                char value = evt.getKeyChar();
                if (value <'0' || value >'9') evt.consume();
            }
        }); 
        
        txtCantidad.setEditable(false);
        txtCantidad.setText(Integer.toString(lienzo.getTiempo().getImagenes().size()));
        //Agregando lista de imagenes
        lienzo.getTiempo().getImagenes().stream().map((img) -> {
            comboBoxListaImagenes.addItem(img.getId());
            return img;
        }).map((img) -> {
            comboBoxListaInicio.addItem(img.getId());
            return img;
        }).forEachOrdered((img) -> {
            comboBoxListaFin.addItem(img.getId());
        });
        
        comboBoxListaImagenes.setSelectedItem(lienzo.getTiempo().getInicio());
        txtDuraciones.setText(Integer.toString(lienzo.retornaImg(lienzo.getTiempo().getInicio()).getDuracion()));
        
        comboBoxListaInicio.setSelectedItem(lienzo.getTiempo().getInicio());
        comboBoxListaFin.setSelectedItem(lienzo.getTiempo().getFin());
        
        comboBoxListaImagenes.addMouseListener(new MouseAdapter() {
                @Override
                public void mousePressed(MouseEvent e) {
                    if (e.getButton() == MouseEvent.BUTTON1) {
                        if (!txtDuraciones.getText().isEmpty()) {
                            lienzo.modificarDuracion(Integer.parseInt(txtDuraciones.getText()), comboBoxListaImagenes.getSelectedItem().toString());
                        }
                        lienzo.getTiempo().setInicio(comboBoxListaInicio.getSelectedItem().toString());
                        lienzo.getTiempo().setFin(comboBoxListaFin.getSelectedItem().toString());
                        comprobarColores(lienzo, jtf, comboBoxListaImagenes.getSelectedItem().toString());
                    }
                }
            });
        
        comboBoxListaImagenes.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                txtDuraciones.setText(Integer.toString(searchDuracion(lienzo, comboBoxListaImagenes.getSelectedItem().toString()).getDuracion()));
                repintar(jtf, x.getDimension(), y.getDimension(), lienzo.getFondo().getColor());
                pintar(jtf, lienzo.listaPintarID(comboBoxListaImagenes.getSelectedItem().toString()));
                idActual.setId(comboBoxListaImagenes.getSelectedItem().toString());
            }
        });
        
        btnGenerar.addActionListener(new java.awt.event.ActionListener() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                if (!txtDuraciones.getText().isEmpty()) {
                    lienzo.modificarDuracion(Integer.parseInt(txtDuraciones.getText()), comboBoxListaImagenes.getSelectedItem().toString());
                }
                lienzo.getTiempo().setInicio(comboBoxListaInicio.getSelectedItem().toString());
                lienzo.getTiempo().setFin(comboBoxListaFin.getSelectedItem().toString());
                comprobarColores(lienzo, jtf, comboBoxListaImagenes.getSelectedItem().toString());
                JFileChooser chooser = new JFileChooser();
                chooser.setCurrentDirectory(new File("."));
                chooser.setDialogTitle("Selecciona la carpeta");
                chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
                chooser.setAcceptAllFileFilterUsed(false);
                int opcion = chooser.showDialog(null, "Exportar");
                if (opcion == JFileChooser.APPROVE_OPTION) {
                    if (lienzo.getTipo().equals("png")) {
                        try {
                            me.expotarPNG(lienzo, chooser.getSelectedFile().getAbsolutePath());
                            JOptionPane.showMessageDialog(null, "Exportado: " + chooser.getSelectedFile().getAbsolutePath());
                        } catch (IOException ex) {
                            JOptionPane.showMessageDialog(null, "Error al exportar, vuelva a intentarlo.", "ERROR", JOptionPane.ERROR_MESSAGE);
                        }
                    } else {
                        if (me.exportarGif(lienzo, chooser.getSelectedFile().getAbsolutePath())) {
                            JOptionPane.showMessageDialog(null, "Exportado: " + chooser.getSelectedFile().getAbsolutePath());
                        } else {
                            JOptionPane.showMessageDialog(null, "Error al exportar, vuelva a intentarlo.", "ERROR", JOptionPane.ERROR_MESSAGE);
                        }
                    }
                }
            }
        });
        
        /*Add Paleta */
        panelColores.setLayout(new java.awt.GridLayout(lienzo.getColores().size(), 2));
        for (int i = 0; i < lienzo.getColores().size(); i++) {
            coloresEnPaleta[i] = new JLabel(lienzo.getColores().get(i).getIdColor());
            coloresEnPaleta[i].setSize(120, 15);
            txtFiels[i] = new JTextField();
            txtFiels[i].setSize(100, 15);
            txtFiels[i].setEditable(false);
            txtFiels[i].setBackground(lienzo.getColores().get(i).getColor());//Agregando el Fondo
            txtFiels[i].setOpaque(true);
            txtFiels[i].addMouseListener(new MouseAdapter() {
                @Override
                public void mousePressed(MouseEvent e) {
                    for (int j = 0; j < lienzo.getColores().size(); j++) {
                        if (e.getSource() == txtFiels[j]) {
                            if (e.getButton() == MouseEvent.BUTTON1) {
                                txtSeleccionado.setBackground(txtFiels[j].getBackground());
                            }
                        }
                    }
                }
            });
            panelColores.add(txtFiels[i]);
            panelColores.add(coloresEnPaleta[i]); 
        }   

        panelScrollColores.setViewportView(panelColores);
        
        txtSeleccionado.setEditable(false);
        txtSeleccionado.setBackground(lienzo.getFondo().getColor());
        
        checkBoxBorrador.setText("Borrador");
        
        javax.swing.GroupLayout panelHerramientasLayout = new javax.swing.GroupLayout(panelHerramientas);
        panelHerramientas.setLayout(panelHerramientasLayout);
        panelHerramientasLayout.setHorizontalGroup(
            panelHerramientasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelHerramientasLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panelHerramientasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelHerramientasLayout.createSequentialGroup()
                        .addComponent(lblImagen)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnGenerar))
                    .addGroup(panelHerramientasLayout.createSequentialGroup()
                        .addGroup(panelHerramientasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(comboBoxListaImagenes, javax.swing.GroupLayout.Alignment.LEADING, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, panelHerramientasLayout.createSequentialGroup()
                                .addComponent(lblCantidadImagenes)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txtCantidad, javax.swing.GroupLayout.PREFERRED_SIZE, 55, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(12, 12, 12)
                        .addGroup(panelHerramientasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(panelHerramientasLayout.createSequentialGroup()
                                .addComponent(lblDuracion)
                                .addGap(0, 0, Short.MAX_VALUE))
                            .addComponent(txtDuraciones)))
                    .addGroup(panelHerramientasLayout.createSequentialGroup()
                        .addGroup(panelHerramientasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelHerramientasLayout.createSequentialGroup()
                                .addGroup(panelHerramientasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(lblColores)
                                    .addGroup(panelHerramientasLayout.createSequentialGroup()
                                        .addComponent(lblColorSeleccionado)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(txtSeleccionado, javax.swing.GroupLayout.PREFERRED_SIZE, 63, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(checkBoxBorrador))
                            .addComponent(panelScrollColores)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelHerramientasLayout.createSequentialGroup()
                                .addGroup(panelHerramientasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(lblInicio)
                                    .addComponent(lblFin))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGroup(panelHerramientasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(comboBoxListaFin, 0, 160, Short.MAX_VALUE)
                                    .addComponent(comboBoxListaInicio, 0, 160, Short.MAX_VALUE))))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addGap(20, 20, 20))
        );
        panelHerramientasLayout.setVerticalGroup(
            panelHerramientasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelHerramientasLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panelHerramientasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblImagen)
                    .addComponent(btnGenerar))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panelHerramientasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(comboBoxListaImagenes, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblDuracion))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panelHerramientasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblCantidadImagenes)
                    .addComponent(txtCantidad, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtDuraciones, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panelHerramientasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(comboBoxListaInicio, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblInicio))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(panelHerramientasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblFin)
                    .addComponent(comboBoxListaFin, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblColores)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panelHerramientasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtSeleccionado, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(checkBoxBorrador)
                    .addComponent(lblColorSeleccionado))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(panelScrollColores, javax.swing.GroupLayout.DEFAULT_SIZE, 366, Short.MAX_VALUE))
        );
        
        //Agregando el diseño
        panelAddImagen.setLayout(new AbsoluteLayout());
        
        if (filas*pixels.getDimension() <= 560) {
            contadorAncho = (560 - (filas*pixels.getDimension()))/2;
        }   if (columnas*pixels.getDimension() <= 600) {
            contadorAlto = (600 - (pixels.getDimension()*columnas))/2;
        }   
        
        for (int i = 0; i < filas; i++) {
            for (int j = 0; j < columnas; j++) {
                jtf[i][j] = new JLabel();
                jtf[i][j].setBackground(lienzo.getFondo().getColor());
                jtf[i][j].setOpaque(true);
                jtf[i][j].addMouseListener(new MouseAdapter() {
                    @Override
                    public  void mousePressed(MouseEvent e){
                        for (int k = 0; k < x.getDimension(); k++) {
                            for (int l = 0; l < y.getDimension(); l++) {
                                if (e.getSource() == jtf[k][l] && e.getButton() == MouseEvent.BUTTON1) {
                                    if (checkBoxBorrador.isSelected()) {
                                        jtf[k][l].setBackground(lienzo.getFondo().getColor());
                                    } else {
                                        jtf[k][l].setBackground(txtSeleccionado.getBackground());
                                    }
                                }
                            }
                        }
                    }
                });
                panelAddImagen.add(jtf[i][j], new AbsoluteConstraints(contadorAncho + (i*pixels.getDimension()), contadorAlto + (j*pixels.getDimension()), pixels.getDimension(), pixels.getDimension()));
            }
        }   
        
        pintar(jtf, lienzo.listaPintarID(idActual.getId()));
        
        panelScrollImagen.setViewportView(panelAddImagen);
        javax.swing.GroupLayout panelPrincipalPestañaLayout = new javax.swing.GroupLayout(panelPrincipalPestaña);
        panelPrincipalPestaña.setLayout(panelPrincipalPestañaLayout);
        panelPrincipalPestañaLayout.setHorizontalGroup(
                panelPrincipalPestañaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(panelPrincipalPestañaLayout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(panelHerramientas, javax.swing.GroupLayout.PREFERRED_SIZE, 266, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(panelScrollImagen)
                                .addContainerGap())
        );  panelPrincipalPestañaLayout.setVerticalGroup(
                              panelPrincipalPestañaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(panelPrincipalPestañaLayout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(panelPrincipalPestañaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(panelScrollImagen)
                                        .addComponent(panelHerramientas, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addContainerGap())
        );  javax.swing.GroupLayout pestañaNuevaLayout = new javax.swing.GroupLayout(pestañaNueva);
        pestañaNueva.setLayout(pestañaNuevaLayout);
        pestañaNuevaLayout.setHorizontalGroup(
                pestañaNuevaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(panelPrincipalPestaña, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );  pestañaNuevaLayout.setVerticalGroup(
                pestañaNuevaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(panelPrincipalPestaña, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );  
        
        pestaña.addTab(tituloPestaña, pestañaNueva);
            
    }
    
}
