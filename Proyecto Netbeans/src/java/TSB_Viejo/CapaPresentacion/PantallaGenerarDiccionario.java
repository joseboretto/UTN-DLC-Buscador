/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package TSB_Viejo.CapaPresentacion;

import TSB_Viejo.CapaLogicaDeNegocios.Archivo;
import TSB_Viejo.CapaLogicaDeNegocios.Diccionario;
import TSB_Viejo.CapaLogicaDeNegocios.GestorDiccionarios;
import java.awt.Cursor;
import java.io.File;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.SwingConstants;

import javax.swing.SwingWorker;
import javax.swing.border.LineBorder;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableModel;


/**
 *
 * @author Jose
 */
public class PantallaGenerarDiccionario extends javax.swing.JFrame {

    private Archivo [] archivosALeer;
    private Diccionario dicionarioMemoria;
    /**
     * Creates new form PantallaGrafica
     */
    public PantallaGenerarDiccionario() {
        initComponents();
        
        lbl_Estado.setText("Aca se infromara el estado del sistema");
        lbl_listaArchivos.setHorizontalAlignment(SwingConstants.CENTER);
        lbl_listaArchivos.setVerticalAlignment(SwingConstants.CENTER);
        // create a line border with the specified color and width
        javax.swing.border.Border border = LineBorder.createGrayLineBorder();
        lbl_listaArchivos.setBorder(border);
    }
    
    
    private void guardarDatosEnDB(){
        SwingWorker worker = new SwingWorker<ImageIcon[], Void>() {
            @Override
            public ImageIcon[] doInBackground() {
                System.out.println("Guardar");
                jProgressBar1.setVisible(true);
                jProgressBar1.setIndeterminate(true);
                jButton1.setEnabled(false);
                jButton2.setEnabled(false);
                jButton3.setEnabled(false);
                jButton4.setEnabled(false);
                setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
                GestorDiccionarios gestor = new GestorDiccionarios();
                try {
                    gestor.GuardarDicionario(dicionarioMemoria);
                } catch (SQLException ex) {

                    lbl_Estado.setText("ERROR: Error de la BD");
                    Logger.getLogger(PantallaGenerarDiccionario.class.getName()).log(Level.SEVERE, null, ex);
                }
                return null;
            }

            @Override
            public void done() {
                setCursor(null); //turn off the wait cursor
                jProgressBar1.setIndeterminate(false);
                jButton1.setEnabled(true);
                jButton2.setEnabled(true);
                jButton3.setEnabled(true);
                jButton4.setEnabled(true);
                lbl_Estado.setText("EXITO: El diccionario se guardo en la base de datos");
                archivosALeer=null;
                dicionarioMemoria=null;
                lbl_listaArchivos.setText("No se selecicono ningun Archivo");
                txt_nombre.setText("");
                DefaultTableModel model = (DefaultTableModel) tablaUI.getModel();
                model.setRowCount(0);
                
            }
        };
        
        worker.execute();
    
    }
    
    
    private void crearDiccionario(){
        SwingWorker worker = new SwingWorker<ImageIcon[], Void>() {
            @Override
            public ImageIcon[] doInBackground() {
                jProgressBar1.setVisible(true);
                jProgressBar1.setIndeterminate(true);
                jButton1.setEnabled(false);
                jButton2.setEnabled(false);
                jButton3.setEnabled(false);
                jButton4.setEnabled(false);
                setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
                GestorDiccionarios gestor = new GestorDiccionarios();
                Diccionario dicionario = gestor.nuevoDiccionario(txt_nombre.getText(), archivosALeer);
                dicionarioMemoria=dicionario;
                //mestro una parte de la db, las 10 primeras filas
                String[] columnas = {"Palabra", "Frecuencia", "Archivos"};
                Object[][] datos  =dicionario.getDatos();
                DefaultTableModel modelo = new DefaultTableModel(datos, columnas);
                tablaUI.setModel(modelo);
                
                return null;
            }

            @Override
            public void done() {
                setCursor(null); //turn off the wait cursor
                jProgressBar1.setIndeterminate(false);
                jButton1.setEnabled(true);
                jButton2.setEnabled(true);
                jButton3.setEnabled(true);
                jButton4.setEnabled(true);
                lbl_Estado.setText("Se mostrarn solo las 10 primeas entradas del diccionario");
                
            }
        };
        
        worker.execute();
    
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        txt_nombre = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        lbl_listaArchivos = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        tablaUI = new javax.swing.JTable();
        jButton2 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        lbl_Estado = new javax.swing.JLabel();
        jButton4 = new javax.swing.JButton();
        jProgressBar1 = new javax.swing.JProgressBar();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setResizable(false);

        jLabel1.setText("Nombre Diccionario: ");

        txt_nombre.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_nombreActionPerformed(evt);
            }
        });

        jLabel2.setText("Cola de Archivos");

        lbl_listaArchivos.setText("\"No se selecicono ningun Archivo\"");
        lbl_listaArchivos.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);

        jButton1.setText("Examinar");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        tablaUI.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null}
            },
            new String [] {
                "Palabra", "Frecuencia", "Archivos"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane1.setViewportView(tablaUI);
        if (tablaUI.getColumnModel().getColumnCount() > 0) {
            tablaUI.getColumnModel().getColumn(1).setResizable(false);
        }

        jButton2.setText("Crear");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jButton3.setText("Guardar");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        lbl_Estado.setText("Estado");

        jButton4.setText("Buscar");
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 111, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 104, Short.MAX_VALUE)
                        .addComponent(jButton4, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(78, 78, 78)
                        .addComponent(jButton3, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel1)
                            .addComponent(jLabel2))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(txt_nombre)
                            .addComponent(lbl_listaArchivos, javax.swing.GroupLayout.DEFAULT_SIZE, 258, Short.MAX_VALUE))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jProgressBar1, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                            .addComponent(jButton1, javax.swing.GroupLayout.DEFAULT_SIZE, 110, Short.MAX_VALUE)))
                    .addComponent(lbl_Estado, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel1)
                        .addComponent(txt_nombre, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jProgressBar1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(10, 10, 10)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(lbl_listaArchivos, javax.swing.GroupLayout.PREFERRED_SIZE, 91, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton1))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 151, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(lbl_Estado, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton2)
                    .addComponent(jButton3)
                    .addComponent(jButton4))
                .addGap(21, 21, 21))
        );

        lbl_listaArchivos.getAccessibleContext().setAccessibleDescription("");

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        
        // TODO add your handling code here:
        archivosALeer = null;
        //Creamos el objeto JFileChooser
        JFileChooser fc=new JFileChooser();
        //Indicamos que podemos seleccionar varios ficheros
        fc.setMultiSelectionEnabled(true);
        //Indicamos lo que podemos seleccionar
        fc.setFileSelectionMode(JFileChooser.FILES_ONLY);
        //Creamos el filtro
        FileNameExtensionFilter filtro = new FileNameExtensionFilter("*.TXT", "txt");
        //Le indicamos el filtro
        fc.setFileFilter(filtro);

        //Abrimos la ventana, guardamos la opcion seleccionada por el usuario
        int seleccion=fc.showOpenDialog(this);
         //Si el usuario, pincha en aceptar
        File[] ficheros = null;
        if(seleccion==JFileChooser.APPROVE_OPTION){
            //Seleccionamos el fichero
            ficheros = fc.getSelectedFiles();
        }
        
        
        if (ficheros != null) {
            Archivo [] archivos = new Archivo[ficheros.length];
            String Cadenaarchivos="<html>";
            for (int i = 0; i < ficheros.length; i++) {
                File fichero = ficheros[i];
                Cadenaarchivos += fichero.getName()+"<br>";
                archivos [i] = new Archivo(ficheros[i].getAbsolutePath());
            }
            Cadenaarchivos+= "</html>";
            lbl_listaArchivos.setText(Cadenaarchivos);
            archivosALeer = archivos;
        }
        
        
    }//GEN-LAST:event_jButton1ActionPerformed

    private void txt_nombreActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_nombreActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_nombreActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        // TODO add your handling code here:
        
        if (!txt_nombre.getText().isEmpty()) {
            
            if (archivosALeer != null) {
                    crearDiccionario();
                    return;
            }
            lbl_Estado.setText("ERROR: Debe seleccionar los archivos a procesar");
            return;
        }
        lbl_Estado.setText("ERROR: Debe ingresar un nombre valido");
                
        
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        // TODO add your handling code here:
        if (dicionarioMemoria!=null) {
              lbl_Estado.setText("Espere....se esta guardando el diccionario creado en la base de datos");
              guardarDatosEnDB();
              return;
        }
        lbl_Estado.setText("Debe Generar el diccionario antes de guardarlo");
    }//GEN-LAST:event_jButton3ActionPerformed

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
        // TODO add your handling code here:
        Busqueda pantallaBuscar = new Busqueda();
        pantallaBuscar.setVisible(true);
        //mato el formualrio
        this.dispose();
    }//GEN-LAST:event_jButton4ActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(PantallaGenerarDiccionario.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(PantallaGenerarDiccionario.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(PantallaGenerarDiccionario.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(PantallaGenerarDiccionario.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new PantallaGenerarDiccionario().setVisible(true);
            }
        });
    }
  
    

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JProgressBar jProgressBar1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lbl_Estado;
    private javax.swing.JLabel lbl_listaArchivos;
    private javax.swing.JTable tablaUI;
    private javax.swing.JTextField txt_nombre;
    // End of variables declaration//GEN-END:variables
}
