package com.billycychan.acmepark.permit_service.adapters.action;

import com.billycychan.acmepark.permit_service.adapters.action.handlers.ActionHandler;
import com.billycychan.acmepark.permit_service.adapters.action.handlers.PermitValidateRequestHandler;
import com.fasterxml.jackson.databind.JsonNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class ActionHandlerRegistry {

    private final Map<String, ActionHandler<JsonNode>> handlers = new HashMap<>();

    @Autowired
    public ActionHandlerRegistry(List<ActionHandler<JsonNode>> handlerList) {
        for (ActionHandler<JsonNode> handler : handlerList) {
            if (handler instanceof PermitValidateRequestHandler) {
                handlers.put(Actions.REQUEST_TP_VALIDATION, handler);
            }
        }
    }

    public ActionHandler<JsonNode> getHandler(String action) {
        return handlers.get(action);
    }
}