package org.exoplatform.samples.listeners;


import org.exoplatform.services.listener.Event;
import org.exoplatform.services.listener.Listener;
import org.exoplatform.services.log.ExoLogger;
import org.exoplatform.services.log.Log;
import org.exoplatform.services.security.ConversationRegistry;
import org.exoplatform.services.security.ConversationState;

public class LoginEventListener extends Listener<ConversationRegistry, ConversationState>{

    // Objet Logger pour cet écouteur
    private static final Log LOG = ExoLogger.getLogger(LoginEventListener.class);

    @Override
    public void onEvent(Event<ConversationRegistry, ConversationState> event) throws Exception{
        // Récupérer la source de cet événement
        ConversationRegistry source = event.getSource();
        // Récupérer les données de l'événement
        ConversationState data = event.getData();
        LOG.info("Un événement a été reçu de {} : l'utilisateur {} était connecté", source.getClass(), data.getIdentity().getUserId());
    }
}
