package com.workshopngine.platform.serviceoperations.operations.application.internal.commandservices;

import com.workshopngine.platform.serviceoperations.operations.domain.model.commands.CreateWorkOrderCommand;
import com.workshopngine.platform.serviceoperations.operations.domain.services.ServiceOrderCommandService;
import org.springframework.stereotype.Service;

@Service
public class ServiceOrderCommandServiceImpl implements ServiceOrderCommandService {
    @Override
    public String handle(CreateWorkOrderCommand command) {
        return "";
    }
}
