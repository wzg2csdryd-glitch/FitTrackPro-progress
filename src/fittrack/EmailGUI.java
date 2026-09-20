package fittrack;

/**
 * The Send Email window (opened from the Memberships tab, administrators only).
 * The administrator can pick a member to pre-fill an expiry reminder, then type
 * the sender's Gmail address, the Gmail app password, the recipient, the subject
 * and the message. All the sending itself is done by EmailSender; this class only
 * checks the input with Validator and shows the result.
 */
public class EmailGUI extends javax.swing.JDialog {

    private javax.swing.JComboBox<String> cmbMember;
    private javax.swing.JTextField txfFrom;
    private javax.swing.JPasswordField pwfPassword;
    private javax.swing.JTextField txfTo;
    private javax.swing.JTextField txfSubject;
    private javax.swing.JTextArea txaMessage;
    private javax.swing.JLabel lblStatus;
    private javax.swing.JButton btnSend;

    /**
     * Builds the window and fills the member drop-down.
     * @param parent the window this dialog belongs to and is centred on
     */
    public EmailGUI(java.awt.Frame parent) {
        super(parent, "Send Email", true);
        buildForm();
        populateMemberCombo();
        pack();
        setLocationRelativeTo(parent);
    }

    /**
     * Creates every label, text box and button and places them with fixed
     * coordinates (AbsoluteLayout), in the same style as the other screens.
     */
    private void buildForm() {
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());
        getContentPane().setBackground(Theme.BACKGROUND);

        addLabel("Member (optional):", 15, 15);
        cmbMember = new javax.swing.JComboBox<>();
        cmbMember.addActionListener(e -> memberChosen());
        getContentPane().add(cmbMember, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 12, 370, 25));

        addLabel("From (Gmail address):", 15, 52);
        txfFrom = new javax.swing.JTextField();
        getContentPane().add(txfFrom, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 49, 370, 25));

        addLabel("Gmail app password:", 15, 87);
        pwfPassword = new javax.swing.JPasswordField();
        getContentPane().add(pwfPassword, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 84, 370, 25));
        javax.swing.JLabel hint = new javax.swing.JLabel("Used once to send, never saved. A normal Gmail password will not work.");
        hint.setFont(new java.awt.Font("Arial", java.awt.Font.ITALIC, 11));
        getContentPane().add(hint, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 111, 370, 16));

        addLabel("To:", 15, 137);
        txfTo = new javax.swing.JTextField();
        getContentPane().add(txfTo, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 134, 370, 25));

        addLabel("Subject:", 15, 172);
        txfSubject = new javax.swing.JTextField();
        getContentPane().add(txfSubject, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 169, 370, 25));

        addLabel("Message:", 15, 207);
        txaMessage = new javax.swing.JTextArea();
        txaMessage.setLineWrap(true);
        txaMessage.setWrapStyleWord(true);
        getContentPane().add(new javax.swing.JScrollPane(txaMessage),
                new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 204, 370, 170));

        lblStatus = new javax.swing.JLabel(" ");
        lblStatus.setFont(Theme.MESSAGE_FONT);
        getContentPane().add(lblStatus, new org.netbeans.lib.awtextra.AbsoluteConstraints(15, 385, 510, 20));

        btnSend = makeButton("Send");
        btnSend.addActionListener(e -> sendClicked());
        getContentPane().add(btnSend, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 415, 110, 30));
        javax.swing.JButton btnClose = makeButton("Close");
        btnClose.addActionListener(e -> dispose());
        getContentPane().add(btnClose, new org.netbeans.lib.awtextra.AbsoluteConstraints(270, 415, 90, 30));

        getContentPane().setPreferredSize(new java.awt.Dimension(540, 460));
    }

    /**
     * Adds a label in the form's label font.
     * @param text the label text
     * @param x distance from the left edge
     * @param y distance from the top edge
     */
    private void addLabel(String text, int x, int y) {
        javax.swing.JLabel label = new javax.swing.JLabel(text);
        label.setFont(Theme.LABEL_FONT);
        getContentPane().add(label, new org.netbeans.lib.awtextra.AbsoluteConstraints(x, y, 135, 20));
    }

    /**
     * Makes a button in the program's dark blue button style.
     * @param text the button text
     * @return the new button (not yet added to the form)
     */
    private javax.swing.JButton makeButton(String text) {
        javax.swing.JButton button = new javax.swing.JButton(text);
        button.setFont(Theme.BUTTON_FONT);
        button.setBackground(Theme.ACCENT_DARK_BLUE);
        button.setForeground(java.awt.Color.WHITE);
        button.setOpaque(true);
        button.setBorderPainted(false);
        return button;
    }

    /**
     * Fills the member drop-down with "Select option" and every member's full name.
     */
    private void populateMemberCombo() {
        cmbMember.removeAllItems();
        cmbMember.addItem("Select option");
        for (int i = 0; i < Manager.memberArray.getSize(); i++) {
            cmbMember.addItem(Manager.memberArray.getMember(i).getFullName());
        }
    }

    /**
     * Runs when a member is chosen: writes that member's expiry reminder into the
     * subject and message boxes, and puts their contact into To if it is an e-mail
     * address (contact details are often a phone number, in which case To is left
     * for the administrator to type).
     */
    private void memberChosen() {
        int index = cmbMember.getSelectedIndex();
        if (index <= 0) {
            return;
        }
        Member m = Manager.memberArray.getMember(index - 1);
        String[] reminder = Manager.membershipArray.reminderFor(m, java.time.LocalDate.now());
        txfSubject.setText(reminder[0]);
        txaMessage.setText(reminder[1]);
        txaMessage.setCaretPosition(0);
        if (Validator.isValidEmail(m.getContactDetails())) {
            txfTo.setText(m.getContactDetails().trim());
            showStatus("Reminder prepared for " + m.getFullName() + ".", false);
        } else {
            txfTo.setText("");
            showStatus("Reminder prepared. " + m.getFullName() + "'s contact is not an e-mail address: type it in To.", false);
        }
    }

    /**
     * Runs when Send is clicked: checks every box, then sends on a background
     * thread (a SwingWorker) so the window does not freeze while the mail server
     * answers, and shows the outcome when it is done. The password box is cleared
     * straight away so it never stays on screen.
     */
    private void sendClicked() {
        final String from = txfFrom.getText().trim();
        final String password = new String(pwfPassword.getPassword());
        final String to = txfTo.getText().trim();
        final String subject = txfSubject.getText().trim();
        final String body = txaMessage.getText().trim();

        if (!Validator.isValidEmail(from)) {
            showStatus("From must be a valid e-mail address.", true);
            return;
        }
        if (!Validator.isPresent(password)) {
            showStatus("Enter your Gmail app password.", true);
            return;
        }
        if (!Validator.isValidEmail(to)) {
            showStatus("To must be one valid e-mail address.", true);
            return;
        }
        if (!Validator.isPresent(subject)) {
            showStatus("Subject is required.", true);
            return;
        }
        if (!Validator.isPresent(body)) {
            showStatus("Message is required.", true);
            return;
        }

        pwfPassword.setText("");
        btnSend.setEnabled(false);
        lblStatus.setForeground(Theme.ACCENT_DARK_BLUE);
        lblStatus.setText("Sending...");

        new javax.swing.SwingWorker<String, Void>() {
            @Override
            protected String doInBackground() {
                return EmailSender.send(from, password, to, subject, body);
            }

            @Override
            protected void done() {
                String result;
                try {
                    result = get();
                } catch (Exception ex) {
                    result = "Could not send the e-mail.";
                }
                btnSend.setEnabled(true);
                if (result.isEmpty()) {
                    showStatus("E-mail sent to " + to + ".", false);
                } else {
                    showStatus(result, true);
                }
            }
        }.execute();
    }

    /**
     * Shows a message under the message box in red (error) or green (success).
     * @param text the message
     * @param isError true for red error text, false for green success text
     */
    private void showStatus(String text, boolean isError) {
        lblStatus.setForeground(isError ? Theme.ERROR_RED : Theme.SUCCESS_GREEN);
        lblStatus.setText(text);
    }
}
