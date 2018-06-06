package domain;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
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
        Integer minute = 0 , second = 0, millesimas = 0;
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
                millesimas += 4;

                //Cuando llega a 1000 osea 1 segundo aumenta 1 segundo
                //y las milesimas de segundo de nuevo a 0
                if( millesimas == 1000 )
                {
                    millesimas = 0;
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

                if( millesimas < 10 ) mil = "00" + millesimas;
                else if( millesimas < 100 ) mil = "0" + millesimas;
                else mil = millesimas.toString();

                //Colocamos en la etiqueta la informacion
                time.setText( min + ":" + seg + ":" + mil );
                timeObtained = time.getText();
            System.err.println(timeObtained);
            }
        }catch(Exception e){}
        //Cuando se reincie se coloca nuevamente en 00:00:000
        time.setText( "00:00:000" );
    }



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
