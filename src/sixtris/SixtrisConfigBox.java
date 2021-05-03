/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * SixtrisConfigBox.java
 *
 * Created on 8 avr. 2011, 19:04:37
 */

package sixtris;

/**
 *
 * @author laurent
 */
public class SixtrisConfigBox extends javax.swing.JDialog {

    /** Creates new form SixtrisConfigBox */
    public SixtrisConfigBox(java.awt.Frame parent) {
			super(parent);
      initComponents();
    }


  // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
  private void initComponents() {

    jLabel1 = new javax.swing.JLabel();

    org.jdesktop.application.ResourceMap resourceMap = org.jdesktop.application.Application.getInstance(sixtris.SixtrisApp.class).getContext().getResourceMap(SixtrisConfigBox.class);
    setTitle(resourceMap.getString("configBox.title")); // NOI18N
    setIconImage(null);
    setModal(true);
    setName("configBox"); // NOI18N
    setResizable(false);

    jLabel1.setText(resourceMap.getString("jLabel1.text")); // NOI18N
    jLabel1.setName("jLabel1"); // NOI18N

    javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
    getContentPane().setLayout(layout);
    layout.setHorizontalGroup(
      layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
      .addGroup(layout.createSequentialGroup()
        .addGap(68, 68, 68)
        .addComponent(jLabel1)
        .addContainerGap(248, Short.MAX_VALUE))
    );
    layout.setVerticalGroup(
      layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
      .addGroup(layout.createSequentialGroup()
        .addGap(125, 125, 125)
        .addComponent(jLabel1)
        .addContainerGap(160, Short.MAX_VALUE))
    );

    pack();
  }// </editor-fold>//GEN-END:initComponents

  // Variables declaration - do not modify//GEN-BEGIN:variables
  private javax.swing.JLabel jLabel1;
  // End of variables declaration//GEN-END:variables

}
