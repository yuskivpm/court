package com.dsa.service.crud;

import com.dsa.controller.ControllerRequest;
import com.dsa.controller.ControllerResponse;
import com.dsa.controller.ResponseType;
import com.dsa.dao.AbstractEntityDao;
import com.dsa.dao.DbPoolException;
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
        case PREPARE_UPDATE_FORM:
          return prepareEditForm(request, controllerResponse);
        case CREATE:
        case UPDATE:
        case READ_ALL:
        case READ:
        case DELETE:
          return executeCrudOperation(request, controllerResponse, ce);
      }
    }
    controllerResponse.setResponseType(ResponseType.FAIL);
    return controllerResponse;
  }

  @NotNull
  @Contract("_, _, _ -> param2")
  private ControllerResponse executeCrudOperation(ControllerRequest request, ControllerResponse controllerResponse, CrudEnum ce) {
    String responseValue = "";
    try {
      String id = request.getParameter("id");
      boolean result = false;
      String errorMessage = "\"db error\"";
      String successMessage = "";
      try (D entityDao = createEntityDao()) {
        switch (ce) {
          case CREATE:
          case UPDATE:
            E entity = createEntityFromParameters(request, getNotNull(id, 0));
            if (entity != null) {
              result = ce == CrudEnum.CREATE ? entityDao.createEntity(entity) : entityDao.updateEntity(entity);
            } else {
              errorMessage = "\"All fields are required\"";
            }
            break;
          case READ_ALL:
            List<E> entities = entityDao.readAll();
            successMessage = "[" + entities.stream().map(E::toString).collect(Collectors.joining(",")) + "]";
            result = true;
            break;
          case READ:
            successMessage = entityDao.readEntity(getNotNull(id, 0)).toString();
            result = !successMessage.isEmpty();
            break;
          case DELETE:
            result = entityDao.deleteEntity(id);
            break;
        }
        responseValue = result ? "{\"status\":\"ok\"" + successMessage + "}" : "{\"error\":" + errorMessage + "}";
      }
    } catch (Exception e) {
      responseValue = "{\"error\":\"Exception in createOrUpdateEntity(): " + e + "\"}";
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

  public String getPath() {
    return path;
  }

  protected boolean checkAuthority(ControllerRequest request, ControllerResponse controllerResponse) {
    return true;
  }

  protected abstract E createEntityFromParameters(ControllerRequest request, long id);

  protected abstract D createEntityDao() throws SQLException, DbPoolException;

}
