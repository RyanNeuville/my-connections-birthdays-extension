# 🚀 Créer un service REST sur eXo Platform

## 1️⃣ Préparation

* Copier votre extension dans `$EXO_HOME/sources/` et renommer :

  ```bash
  cp my-extension my-connections-birthdays-extension
  ```

* Créer le package :
# 🚀 Créer un service REST sur eXo Platform



  ```
  my-connections-birthdays-extension/services/src/main/java/org/exoplatform/samples/birthday
  ```

## 2️⃣ Service REST

Créer la classe `MyConnectionsBirthdayRESTService.java` :

```java
package org.exoplatform.samples.birthday;

import org.exoplatform.commons.utils.ListAccess;
import org.exoplatform.services.log.ExoLogger;
import org.exoplatform.services.log.Log;
import org.exoplatform.services.rest.resource.ResourceContainer;
import org.exoplatform.social.core.identity.model.Identity;
import org.exoplatform.social.core.manager.IdentityManager;
import org.exoplatform.social.core.manager.RelationshipManager;
import org.json.JSONArray;
import org.json.JSONObject;

import javax.annotation.security.RolesAllowed;
import javax.ws.rs.*;
import javax.ws.rs.core.*;
import java.util.*;

@Path("connections/birthday")
public class MyConnectionsBirthdayRESTService implements ResourceContainer {
    private static final Log LOG = ExoLogger.getLogger(MyConnectionsBirthdayRESTService.class);
    private IdentityManager identityManager;
    private RelationshipManager relationshipManager;
    private static String BIRTHDAY_PROPERTY = "birthday";

    public MyConnectionsBirthdayRESTService(IdentityManager identityManager, RelationshipManager relationshipManager) {
        this.identityManager = identityManager;
        this.relationshipManager = relationshipManager;
    }

    @GET
    @RolesAllowed("users")
    public Response getMyConnectionsBirthday(@Context SecurityContext securityContext) {
        String user = securityContext.getUserPrincipal().getName();
        try {
            Identity me = identityManager.getOrCreateUserIdentity(user);
            ListAccess<Identity> connections = relationshipManager.getConnections(me);
            Identity[] ids = connections.load(0, connections.getSize());

            JSONArray arr = new JSONArray();
            for (Identity c : ids) {
                JSONObject json = new JSONObject();
                json.put("userName", c.getRemoteId());
                json.put("fullName", c.getProfile().getFullName());
                json.put("avatar", c.getProfile().getAvatarUrl());
                json.put(BIRTHDAY_PROPERTY, c.getProfile().getProperty(BIRTHDAY_PROPERTY));
                arr.put(json);
            }
            return Response.ok(arr.toString()).build();
        } catch (Exception e) {
            LOG.error("Error loading birthdays for {}", user, e);
            return Response.serverError().build();
        }
    }
}
```

## 3️⃣ Configuration

Dans `webapp/src/main/webapp/WEB-INF/conf/configuration.xml` :

```xml
<configuration xmlns="http://www.exoplatform.org/xml/ns/kernel_1_2.xsd">
  <component>
    <key>org.exoplatform.samples.birthday.MyConnectionsBirthdayRESTService</key>
    <type>org.exoplatform.samples.birthday.MyConnectionsBirthdayRESTService</type>
  </component>
</configuration>
```

## 4️⃣ Build & Déploiement

```bash
cd $EXO_HOME/sources/my-connections-birthdays-extension
mvn clean install
cp services/target/my-connections-birthday-services.jar $EXO_HOME/lib
cp webapp/target/my-connections-birthday-webapp.war $EXO_HOME/webapps
```

## 5️⃣ Docker

Dans `docker-compose.yml` :

```yaml
volumes:
  - $EXO_HOME/webapps/my-connections-birthday-webapp.war:/opt/exo/webapps/my-connections-birthday-webapp.war
  - $EXO_HOME/lib/my-connections-birthday-services.jar:/opt/exo/lib/my-connections-birthday-services.jar
```

Démarrer :

```bash
docker-compose -f $EXO_HOME/docker-compose.yml up
```

## 6️⃣ Test

* GET :

  ```
  http://localhost:9099/rest/private/connections/birthday
  ```

  🔑 Authentification Basic (user/pass eXo)

* Réponse :

  ```json
  [
    {
      "userName": "john",
      "fullName": "John Doe",
      "avatar": "http://...",
      "birthday": "1995-04-12"
    }
  ]
  ```