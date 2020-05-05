package com.dsa.service.crud;

import com.dsa.controller.ControllerRequest;
import com.dsa.controller.ControllerResponse;
import com.dsa.controller.ResponseType;
import com.dsa.dao.AbstractEntityDao;
import com.dsa.dao.service.DbPoolException;
import com.dsa.domain.MyEntity;
import com.dsa.service.command.RedirectCommand;

import org.apache.log4j.Logger;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.text.ParseException;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;
import java.util.function.BiFunction;
import java.util.stream.Collectors;

public abstract class AbstractCrud<E extends MyEntity, D extends AbstractEntityDao>
    implements BiFunction<ControllerRequest, ControllerResponse, ControllerResponse> {
  private static final Logger log = Logger.getLogger(AbstractCrud.class);
  protected String path = "";

  @Contract(pure = true)
  public AbstractCrud(String path) {
    this.path = path;
  }

  @Override
  public ControllerResponse apply(@NotNull ControllerRequest request, ControllerResponse controllerResponse) {
    if (checkAuthority(request, controllerResponse)) {
      CrudEnum ce = CrudParser.getCrudOperation(request, getPath());
      switch (ce) {
        case CREATE:
        case UPDATE:
          return createOrUpdateEntity(request, controllerResponse, ce == CrudEnum.CREATE);
        case READ:
        case READ_ALL:
          return readEntity(request, controllerResponse, ce == CrudEnum.READ_ALL);
        case DELETE:
          return deleteEntity(request, controllerResponse);
        case PREPARE_UPDATE_FORM:
          return prepareEditForm(request, controllerResponse);
        case WRONG:
          return wrongCommand(request, controllerResponse);
        case UNKNOWN:
          return unknownCommand(request, controllerResponse);
        default: //skip
      }
    }
    controllerResponse.setResponseType(ResponseType.FAIL);
    return controllerResponse;
  }

  @NotNull
  @Contract("_, _, _ -> param2")
  private ControllerResponse createOrUpdateEntity(
      ControllerRequest request,
      ControllerResponse controllerResponse,
      boolean create
  ) {
    String responseValue = "";
    try {
      long id = 0;
      if (!create) {
        id = Long.parseLong(request.getParameter("id"));
      }
      E entity = createEntityFromParameters(request, id);
      if (entity != null) {
        boolean result;
        try (D entityDao = createEntityDao()) {
          result = create ? entityDao.createEntity(entity) : entityDao.updateEntity(entity);
        }
        if (result) {
          responseValue = "{\"status\":\"ok\"}";
        } else {
          responseValue = "{\"error\":\"db error\"}";
        }
      } else {
        responseValue = "{\"error\":\"All fields are required\"}";
      }
    } catch (Exception e) {
      responseValue = "{\"error\":\"Exception in createOrUpdateEntity(): " + e + "\"}";
    }
    controllerResponse.setResponseType(ResponseType.PLAIN_TEXT);
    controllerResponse.setResponseValue(responseValue);
    return controllerResponse;
  }

  @Contract(pure = true)
  protected static String getNotNull(@NotNull String tryValue, String defaultValue) {
    return tryValue.isEmpty() ? defaultValue : tryValue.trim();
  }

  protected static long getNotNull(@NotNull String tryValue, long defaultValue) {
    return tryValue.isEmpty() ? defaultValue : Long.parseLong(tryValue);
  }

  protected static Date getNotNull(@NotNull String tryValue, Date defaultValue) throws ParseException {
    return tryValue.isEmpty() ? defaultValue : MyEntity.strToDate(tryValue);
  }

  @NotNull
  @Contract("_, _ -> param2")
  private ControllerResponse deleteEntity(ControllerRequest request, ControllerResponse controllerResponse) {
    String responseValue = "";
    try {
      String id = request.getParameter("id");
      boolean result;
      try (D entityDao = createEntityDao()) {
        result = entityDao.deleteEntity(id);
      }
      if (result) {
        responseValue = "{\"status\":\"ok\"}";
      } else {
        responseValue = "{\"error\":\"db error\"}";
      }
    } catch (Exception e) {
      responseValue = "{\"error\":\"Exception in deleteEntity(): " + e + "\"}";
    }
    controllerResponse.setResponseType(ResponseType.PLAIN_TEXT);
    controllerResponse.setResponseValue(responseValue);
    return controllerResponse;
  }

  @NotNull
  @Contract("_, _, _ -> param2")
  private ControllerResponse readEntity(ControllerRequest request, ControllerResponse controllerResponse, boolean readAll) {
    String responseValue = "";
    try {
      long id = 0;
      if (!readAll) {
        id = Long.parseLong(request.getParameter("id"));
      }
      List<E> entities = null;
      String responseText = "";
      try (D entityDao = createEntityDao()) {
        if (readAll) {
          entities = entityDao.readAll();
          responseText = "[" +
              entities.stream()
                  .map(E::toString)
                  .collect(Collectors.joining(",")) +
              "]";
        } else {
          responseText = entityDao.readEntity(id).toString();
        }
      }
      if (!responseText.isEmpty()) {
        responseValue = "{" +
            "\"status\":\"ok\"," +
            "\"data\":" + responseText + "}";
      } else {
        responseValue = "{\"error\":\"db error\"}";
      }
    } catch (Exception e) {
      responseValue = "{\"error\":\"Exception in readEntity(): " + e + "\"}";
    }
    controllerResponse.setResponseType(ResponseType.PLAIN_TEXT);
    controllerResponse.setResponseValue(responseValue);
    return controllerResponse;
  }

  @NotNull
  private ControllerResponse prepareEditForm(@NotNull ControllerRequest request, @NotNull ControllerResponse controllerResponse) {
    String id = request.getParameter("id");
    try (D entityDao = createEntityDao()) {
      MyEntity entity = entityDao.loadAllSubEntities(entityDao.readEntity("ID", id));
      if (entity != null) {
        controllerResponse = new RedirectCommand().apply(request, controllerResponse);
        controllerResponse.setAttribute("editEntity", entity);
        return controllerResponse;
      }
    } catch (Exception e) {
      log.error("Fail get User in prepareEditForm for Entity.id(" + id + "): " + e);
    }
    controllerResponse.setResponseType(ResponseType.FAIL);
    return controllerResponse;
  }


  protected ControllerResponse wrongCommand(@NotNull ControllerRequest request, @NotNull ControllerResponse controllerResponse) {
    controllerResponse.setResponseType(ResponseType.FAIL);
    return controllerResponse;
  }

  protected ControllerResponse unknownCommand(@NotNull ControllerRequest request, @NotNull ControllerResponse controllerResponse) {
    controllerResponse.setResponseType(ResponseType.FAIL);
    return controllerResponse;
  }

  public String getPath() {
    return path;
  }

  protected boolean checkAuthority(ControllerRequest request, ControllerResponse controllerResponse) {
    return true;
  }

  protected abstract E createEntityFromParameters(ControllerRequest request, long id);

  protected abstract D createEntityDao() throws SQLException, DbPoolException;

}
