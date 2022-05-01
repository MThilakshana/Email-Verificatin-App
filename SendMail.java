import static javax.swing.JOptionPane.YES_NO_OPTION;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Properties;
import java.util.Random;
import javax.mail.PasswordAuthentication;
import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;



class SendMail extends JFrame{

    int num;

    SendMail(){
        // add cursor -------------------------

        Cursor cur = new Cursor(Cursor.HAND_CURSOR);

        // add font----------------------------
        Font f1 = new Font("Rockwell" ,Font.PLAIN,30);
        Font f2 = new Font("Rockwell" ,Font.PLAIN,20);

        // add text box -----------------------

        JTextField mail = new JTextField();
        mail.setBounds(90,135,370,45);
        mail.setFont(f2);
        mail.setBackground(new Color(160,215,176));
        mail.setHorizontalAlignment(SwingConstants.CENTER);
        add(mail);

        JTextField otp = new JTextField();
        otp.setBounds(245,236,213,45);
        otp.setFont(f2);
        otp.setBackground(new Color(160,215,176));
        otp.setHorizontalAlignment(SwingConstants.CENTER);
        add(otp);

        // add button --------------------------

        JButton send = new JButton("Send OTP");
        send.setBounds(90,236,129,44);
        send.setFont(f2);
        send.setCursor(cur);
        add(send);

        JButton ok = new JButton("Enter");
        ok.setBounds(90,324,129,44);
        ok.setFont(f2);
        ok.setCursor(cur);
        add(ok);

        JButton exit = new JButton("Exit");
        exit.setBounds(331,324,129,44);
        exit.setFont(f2);
        exit.setCursor(cur);
        add(exit);

        // add Labels -------------------------

        JLabel lb1 = new JLabel("E-mail Verification System");
        lb1.setBounds(0,10,550,35);
        lb1.setForeground(new Color(248,255,246));
        lb1.setFont(f1);
        lb1.setHorizontalAlignment(SwingConstants.CENTER);
        add(lb1);

        JLabel lb2 = new JLabel("Enter E-mail Address");
        lb2.setBounds(0,95,550,24);
        lb2.setForeground(new Color(248,255,246));
        lb2.setFont(f2);
        lb2.setHorizontalAlignment(SwingConstants.CENTER);
        add(lb2);

        JLabel lb3 = new JLabel("Enter OTP Code");
        lb3.setBounds(0,196,550,24);
        lb3.setForeground(new Color(248,255,246));
        lb3.setFont(f2);
        lb3.setHorizontalAlignment(SwingConstants.CENTER);
        add(lb3);

        // add panel --------------------------
        JPanel pan = new JPanel();
        pan.setBounds(0,0,550,450);
        pan.setBackground(new Color(28,89,37));
        add(pan);

        // design window -----------------------
        setSize(550,450);
        setTitle("Email Verification");
        setLocationRelativeTo(null);
        setResizable(false);
        setVisible(true);

        exit.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                int yes = JOptionPane.showConfirmDialog(null, "Are you want exit?", "Message", YES_NO_OPTION);
                if(yes == JOptionPane.YES_OPTION){
                    System.exit(0);
                }
            }
        });

        send.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){

                // get random number for OTP ---------------

                int max = 999999, min = 100000;
                Random random = new Random();
                num = (int)Math.floor(Math.random()*(max-min +1)+min);

                // sne email -------------------------------

                String toMail = mail.getText();
                String fromEmail = "cadenzalibraryms@gmail.com";
                String password = "Cadenza1234";

                try {
                    Properties prop = new Properties();
                    prop.setProperty("mail.smtp.host", "smtp.gmail.com");
                    prop.setProperty("mail.smtp.port", "465");
                    prop.setProperty("mail.smtp.auth", "true");
                    prop.setProperty("mail.smtp.socketFactory.port", "465");
                    prop.setProperty("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");

                    // connection with email -------------------

                    Session session = Session.getDefaultInstance(prop, new javax.mail.Authenticator(){
                        protected PasswordAuthentication getPasswordAuthentication(){
                            return new PasswordAuthentication(fromEmail, password);
                        } 
                    });

                    Message message = new MimeMessage(session);
                    message.setFrom(new InternetAddress(fromEmail));
                    message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(toMail));
                    message.setSubject("Email Verification System");
                    message.setText("Hello,\nYour OTP code - "+num+"\n\nThis is a one time verification code. but you can generate another by clicking SEND OTP button on Cadenza Email Verification System.\n\nThank You!");

                    // send mail ----------------------------------------------------------------------------

                    Transport.send(message);
                    
                    JOptionPane.showMessageDialog(null, "Please Check Your Email");
                } catch (Exception ex) {
                    //TODO: handle exception
                    System.out.println("Error - "+ex);
                }
               
            }
        });

        ok.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e) {

                String ot = otp.getText();

                if(ot.isEmpty()){
                    JOptionPane.showMessageDialog(null, "Enter OTP");
                }
                else{
                    if(num == Integer.parseInt(otp.getText())){
                        JOptionPane.showMessageDialog(null, "Your Email Verified");
                        dispose();
                        SendMail obj2 = new SendMail();
                    }else{
                        JOptionPane.showMessageDialog(null, "Invalid OTP");
                    }
    
                }
                
            }
        });


    }



    public static void main(String[] args) {

        SendMail obj = new SendMail();

        try{
            UIManager.setLookAndFeel((UIManager.getSystemLookAndFeelClassName()));
        }catch(ClassNotFoundException ex){
        }catch(InstantiationException ex){
        } catch (IllegalAccessException e) {
        } catch (UnsupportedLookAndFeelException e) {
        }
    }
}