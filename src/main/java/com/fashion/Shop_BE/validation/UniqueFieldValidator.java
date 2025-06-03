package com.fashion.Shop_BE.validation;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import jakarta.servlet.http.HttpServletRequest;

@Component
public class UniqueFieldValidator implements ConstraintValidator<UniqueField, Object> {

    @PersistenceContext
    private EntityManager entityManager;

    private String fieldName;
    private String idField;
    private Class<?> entityClass;

    @Override
    public void initialize(UniqueField constraintAnnotation) {
        this.fieldName = constraintAnnotation.fieldName();
        this.idField = constraintAnnotation.idField();
        this.entityClass = constraintAnnotation.entity();
    }

    @Override
    public boolean isValid(Object value, ConstraintValidatorContext context) {
        if (value == null) {
            return true; // Không cần kiểm tra nếu không có giá trị
        }

        Long entityId = getEntityIdFromRequest(idField);
        String queryString = "SELECT COUNT(e) FROM " + entityClass.getSimpleName() + " e WHERE e." + fieldName + " = :value";

        if (entityId != null) {
            queryString += " AND e." + idField + " <> :id";
        }

        Query query = entityManager.createQuery(queryString);
        query.setParameter("value", value);

        if (entityId != null) {
            query.setParameter("id", entityId);
        }

        Long count = (Long) query.getSingleResult();
        return count == 0;
    }

    private Long getEntityIdFromRequest(String idField) {
        RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
        if (requestAttributes instanceof ServletRequestAttributes servletRequestAttributes) {
            HttpServletRequest request = servletRequestAttributes.getRequest();
            String idParam = request.getRequestURI().replaceAll(".*/", ""); // Lấy ID từ URL
            try {
                return idParam != null ? Long.parseLong(idParam) : null;
            } catch (NumberFormatException e) {
                return null;
            }
        }
        return null;
//        RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
//        if (requestAttributes instanceof ServletRequestAttributes servletRequestAttributes) {
//            HttpServletRequest request = servletRequestAttributes.getRequest();
//            String idParam = request.getParameter(idField); // Lấy ID từ request dựa trên idField
//            try {
//                return idParam != null ? Long.parseLong(idParam) : null;
//            } catch (NumberFormatException e) {
//                return null;
//            }
//        }
//        return null;
    }
}