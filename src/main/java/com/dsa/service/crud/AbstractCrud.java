package com.dsa.service.crud;

import com.dsa.controller.ControllerRequest;
import com.dsa.controller.ResponseType;
import com.dsa.dao.AbstractEntityDao;
import com.dsa.dao.DbPoolException;
import com.dsa.domain.IEntity;
import com.dsa.service.command.RedirectCommand;

import org.apache.log4j.Logger;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.text.ParseException;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

public abstract class AbstractCrud<E extends IEntity, D extends AbstractEntityDao>
    implements Function<ControllerRequest, ControllerRequest> {

  protected static final String ID = IEntity.ID;

  private static final Logger log = Logger.getLogger(AbstractCrud.class);
  private static ICrudParser crudParser;

  private final String path;

  @Contract(pure = true)
  protected AbstractCrud(String path) {
    this.path = path;
  }

  @Override
  public ControllerRequest apply(@NotNull ControllerRequest request) {
    if (crudParser != null && checkAuthority(request)) {
      CrudEnum ce = crudParser.getCrudOperation(request, getPath());
      switch (ce) {
        case PREPARE_UPDATE_FORM:
          return prepareEditForm(request);
        case CREATE:
        case UPDATE:
        case READ_ALL:
        case READ:
        case DELETE:
          return executeCrudOperation(request, ce);
      }
    }
    request.setResponseType(ResponseType.FAIL);
    return request;
  }

  @NotNull
  @Contract("_, _ -> param1")
  private ControllerRequest executeCrudOperation(@NotNull ControllerRequest request, @NotNull CrudEnum ce) {
    String responseValue;
    try {
      String id = request.getParameter(ID);
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
            IEntity resultEntity = entityDao.readEntity(getNotNull(id, 0));
            successMessage = resultEntity != null ? resultEntity.toString() : "";
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
    request.setResponseType(ResponseType.PLAIN_TEXT);
    request.setResponseValue(responseValue);
    return request;
  }

  @NotNull
  private ControllerRequest prepareEditForm(@NotNull ControllerRequest request) {
    String id = request.getParameter("id");
    try (D entityDao = createEntityDao()) {
      IEntity entity = entityDao.loadAllSubEntities(entityDao.readEntity(ID, id));
      if (entity != null) {
        request = new RedirectCommand().apply(request);
        request.setAttribute("editEntity", entity);
        return request;
      }
    } catch (Exception e) {
      log.error("Fail get User in prepareEditForm for Entity.id(" + id + "): " + e);
    }
    request.setResponseType(ResponseType.FAIL);
    return request;
  }

  @Contract(pure = true)
  protected static String getNotNull(@NotNull String tryValue, String defaultValue) {
    return tryValue.isEmpty() ? defaultValue : tryValue.trim();
  }

  protected static long getNotNull(@NotNull String tryValue, long defaultValue) {
    return tryValue.isEmpty() ? defaultValue : Long.parseLong(tryValue);
  }

  protected static Date getNotNull(@NotNull String tryValue, Date defaultValue) throws ParseException {
    return tryValue.isEmpty() ? defaultValue : IEntity.strToDate(tryValue);
  }

  public String getPath() {
    return path;
  }

  protected abstract boolean checkAuthority(@NotNull ControllerRequest request);

  protected abstract E createEntityFromParameters(@NotNull ControllerRequest request, long id);

  protected abstract D createEntityDao() throws SQLException, DbPoolException;

  public static void setCrudParser(ICrudParser crudParser){
    AbstractCrud.crudParser = crudParser;
  }

}
