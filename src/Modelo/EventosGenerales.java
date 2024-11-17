package Modelo;

/**
 *
 * @author Emilia
 */
public class EventosGenerales {

    public enum Eventos {
        MESA_CREADA,
        MESA_CERRADA,
        CAMBIA_ESTADO_MESA, // abierta, iniciada, finalizada
        CAMBIA_CANTIDAD_JUGADORES, //jug ingresa o abandona la mesa
        CAMBIA_POZO, //cambia el monto durante las apuestas
        
        INICIA_JUEGO, //mesa iniciada
        FINALIZA_JUEGO, //mesa finalizada               
        ACTUALIZA_SALDO_JUGADOR,
        
        CAMBIO_LISTA_SESIONES,
        INICIA_MANO,
        
        FINALIZA_MANO,
        APUESTA_INICIADA,
        PASAR_APUESTA,
    }
}
