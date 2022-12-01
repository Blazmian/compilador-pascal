package visuals;

import java.awt.Color;

/**
 *
 * @author DemiRascon
 */
public class Main extends javax.swing.JFrame {

    public Main() {
        initComponents();
        this.setLocationRelativeTo(null); // This center the app when run
    }
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        p_mainContainer = new javax.swing.JPanel();
        p_windowsControl = new javax.swing.JPanel();
        p_exit = new javax.swing.JPanel();
        btn_exit = new javax.swing.JLabel();
        p_minimize = new javax.swing.JPanel();
        btn_minimize = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("TecLibrary");
        setMaximumSize(new java.awt.Dimension(1000, 500));
        setMinimumSize(new java.awt.Dimension(1000, 500));
        setUndecorated(true);
        setPreferredSize(new java.awt.Dimension(1000, 500));
        setSize(new java.awt.Dimension(1000, 500));

        p_mainContainer.setBackground(new java.awt.Color(255, 255, 255));
        p_mainContainer.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        p_windowsControl.setBackground(new java.awt.Color(255, 255, 255));
        p_windowsControl.setForeground(new java.awt.Color(51, 51, 51));
        p_windowsControl.setFont(new java.awt.Font("Roboto", 1, 24)); // NOI18N
        p_windowsControl.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        p_exit.setBackground(new java.awt.Color(255, 255, 255));

        btn_exit.setFont(new java.awt.Font("Roboto", 0, 18)); // NOI18N
        btn_exit.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        btn_exit.setText("X");

        javax.swing.GroupLayout p_exitLayout = new javax.swing.GroupLayout(p_exit);
        p_exit.setLayout(p_exitLayout);
        p_exitLayout.setHorizontalGroup(
            p_exitLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(btn_exit, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 40, Short.MAX_VALUE)
        );
        p_exitLayout.setVerticalGroup(
            p_exitLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(btn_exit, javax.swing.GroupLayout.DEFAULT_SIZE, 40, Short.MAX_VALUE)
        );

        p_windowsControl.add(p_exit, new org.netbeans.lib.awtextra.AbsoluteConstraints(960, 0, 40, 40));

        p_minimize.setBackground(new java.awt.Color(255, 255, 255));

        btn_minimize.setFont(new java.awt.Font("Roboto", 0, 24)); // NOI18N
        btn_minimize.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        btn_minimize.setText("-");

        javax.swing.GroupLayout p_minimizeLayout = new javax.swing.GroupLayout(p_minimize);
        p_minimize.setLayout(p_minimizeLayout);
        p_minimizeLayout.setHorizontalGroup(
            p_minimizeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(btn_minimize, javax.swing.GroupLayout.DEFAULT_SIZE, 40, Short.MAX_VALUE)
        );
        p_minimizeLayout.setVerticalGroup(
            p_minimizeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(btn_minimize, javax.swing.GroupLayout.DEFAULT_SIZE, 40, Short.MAX_VALUE)
        );

        p_windowsControl.add(p_minimize, new org.netbeans.lib.awtextra.AbsoluteConstraints(920, 0, 40, 40));

        p_mainContainer.add(p_windowsControl, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 1000, 40));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(p_mainContainer, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(p_mainContainer, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Main().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel btn_exit;
    private javax.swing.JLabel btn_minimize;
    private javax.swing.JPanel p_exit;
    private javax.swing.JPanel p_mainContainer;
    private javax.swing.JPanel p_minimize;
    private javax.swing.JPanel p_windowsControl;
    // End of variables declaration//GEN-END:variables
}
