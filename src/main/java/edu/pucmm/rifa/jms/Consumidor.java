package edu.pucmm.rifa.jms;

import edu.pucmm.rifa.utilidades.IObserver;
import edu.pucmm.rifa.utilidades.SubjectHelper;
import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by vacax on 04/10/15.
 */
public class Consumidor {

    ActiveMQConnectionFactory factory;
    Connection connection;
    Session session;
    Queue queue;
    Topic topic;
    MessageConsumer consumer;
    String cola = "triclinio.rifa";

    private SubjectHelper mensajesListener=new SubjectHelper();


    public Consumidor(){

    }

    /**
     *
     */
    public Consumidor(String cola) {
        this.cola = cola;
    }

    /**
     *
     * @throws JMSException
     */
    public void conectar() throws JMSException {
        System.out.println("Conectando a la cola: "+cola);
        //Creando el connection factory indicando el host y puerto, en la trama el failover indica que reconecta de manera
        // automatica
        factory = new ActiveMQConnectionFactory(".......", "......", "failover:tcp://.......:61616");


        //Crea un nuevo hilo cuando hacemos a conexión, que no se detiene cuando
        // aplicamos el metodo stop(), para eso tenemos que cerrar la JVM o
        // realizar un close().
        connection = factory.createConnection();


        // Arrancamos la conexión
        //Puede verlo en direccion por defecto de tu activemq local:
        //http://127.0.0.1:8161/admin/connections.jsp
        connection.start();

        // Creando una sesión no transaccional y automatica.
        session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);

        // Creamos o nos connectamos a la una cola, por defecto ActiveMQ permite
        // la creación si no existe. Si la cola es del tipo Queue es acumula los mensajes, si es
        // del tipo topic es en el momento.

        //queue = session.createQueue(cola);
        topic = session.createTopic(cola);


        //consumer = session.createConsumer(queue);
        consumer = session.createConsumer(topic);
        consumer.setMessageListener(new MessageListener() {
            @Override
            public void onMessage(Message message) {
                try {
                    //
                    TextMessage messageTexto = (TextMessage) message;
                    System.out.println("El mensaje de texto recibido: " + messageTexto.getText()+" - "+new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(new Date()));
                    //
                    switch (messageTexto.getText()){
                        case "inicio":
                            mensajesListener.notify(this.getClass(), "inicio", null);
                            break;
                        case "aprobado":
                            mensajesListener.notify(this.getClass(), "aprobado", null);
                            break;
                        case "noPresente":
                            mensajesListener.notify(this.getClass(), "noPresente", null);
                            break;
                        case "cancelar":
                            mensajesListener.notify(this.getClass(), "cancelar", null);
                            break;
                        default:
                            System.out.println("No realiza acción");
                    }
                }catch(Exception ex){
                    ex.printStackTrace();
                }
            }
        });
    }


    public void cerrarConexion() throws JMSException {
        connection.stop();
        connection.close();
    }

    public void addMensajeListener(IObserver observer){
        mensajesListener.addObserver(observer);
    }

    public void removeMensajeListener(IObserver observer){
        mensajesListener.removeObserver(observer);
    }
}
