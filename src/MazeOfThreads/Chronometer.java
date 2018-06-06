package MazeOfThreads;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javafx.stage.Stage;

public class Chronometer extends JFrame implements Runnable{
    JLabel time;
    Thread threadTime;
    boolean ActiveChrono;
    Stage thestage;
    
    private String timeObtained;
    private String name;
    
    public static int onoff = 1;

    public Chronometer(String timeObtained, String name) {
        this.timeObtained = timeObtained;
        this.name = name;
    }
    
    public Chronometer(){
        setSize( 170, 130 );
        setDefaultCloseOperation( JFrame.DISPOSE_ON_CLOSE );
        setLayout( new BorderLayout() );
        setResizable(false);

        //Etiqueta donde se colocara el tiempo 
        time = new JLabel( "00:00:000" );
        time.setFont( new Font( Font.SERIF, Font.BOLD, 30 ) );
        time.setHorizontalAlignment( JLabel.CENTER );
        time.setForeground( Color.BLUE );
        time.setBackground( Color.WHITE );
        time.setOpaque( true );

        add(time, BorderLayout.CENTER );

        this.setLocationRelativeTo( null );
//        setVisible( true );
    }

    public void run(){
        Integer minute = 0 , second = 0, milesimas = 0;
        //min es minutos, seg es segundos y mil es milesimas de segundo
        String min="", seg="", mil="";
        try
        {
            //Mientras cronometroActivo sea verdadero entonces seguira
            //aumentando el tiempo
            while( ActiveChrono )
            {
                Thread.sleep( 4 );
                //Incrementamos 4 milesimas de segundo
                milesimas += 4;

                //Cuando llega a 1000 osea 1 segundo aumenta 1 segundo
                //y las milesimas de segundo de nuevo a 0
                if( milesimas == 1000 )
                {
                    milesimas = 0;
                    second += 1;
                    //Si los segundos llegan a 60 entonces aumenta 1 los minutos
                    //y los segundos vuelven a 0
                    if( second == 60 )
                    {
                        second = 0;
                        minute++;
                    }
                }

                //Esto solamente es estetica para que siempre este en formato
                //00:00:000
                if( minute < 10 ) min = "0" + minute;
                else min = minute.toString();
                if( second < 10 ) seg = "0" + second;
                else seg = second.toString();

                if( milesimas < 10 ) mil = "00" + milesimas;
                else if( milesimas < 100 ) mil = "0" + milesimas;
                else mil = milesimas.toString();

                //Colocamos en la etiqueta la informacion
                time.setText( min + ":" + seg + ":" + mil );
                timeObtained = time.getText();
            System.err.println(timeObtained);
            }
        }catch(Exception e){}
        //Cuando se reincie se coloca nuevamente en 00:00:000
        time.setText( "00:00:000" );
    }

//    public void actionPerformed( ActionEvent evt) {
//        Object o = evt.getSource();
//        if( o instanceof JButton )
//        {
//            JButton btn = (JButton)o;
//            if( btn.getText().equals("Reinit") ){
//                if(onoff == 0){
//                   onoff = 1;
//                   initChrono();
//                }
//            }
//            if( btn.getText().equals("Stop") ) {
//                if (onoff == 1){
//                   onoff = 0;
//                   stopChrono();
//                }
//            }
//        }
//    }

    //Iniciar el cronometro poniendo cronometroActivo 
    //en verdadero para que entre en el while
    public void initChrono() {
        ActiveChrono = true;
        threadTime = new Thread( this );
        threadTime.start();
    }

    //Esto es para parar el cronometro
    public void stopChrono(){
        ActiveChrono = false;
    }
    
    public String getTime(){
        return timeObtained;
    }
    
    public String getName(){
        return name;
    }

    public String getTimeObtained() {
        return timeObtained;
    }

    public void setTimeObtained(String timeObtained) {
        this.timeObtained = timeObtained;
    }
    
}
