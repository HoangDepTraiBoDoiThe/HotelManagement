package com.example.hotelmanagement.controller.assembler;

import com.example.hotelmanagement.constants.ApplicationRole;
import com.example.hotelmanagement.controller.BillController;
import com.example.hotelmanagement.dto.response.BaseResponse;
import com.example.hotelmanagement.helper.StaticHelper;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Component
public class BillAssembler {
    public <T extends BaseResponse> EntityModel<T> toModel(T entity, Authentication authentication) {
        Set<String> roles = StaticHelper.extractAllRoles(authentication);
        
        EntityModel<T> entityModel = EntityModel.of(entity, 
                WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(BillController.class).getBillById(entity.getId(), authentication)).withSelfRel().withType("GET")
        );
        if (roles.contains(ApplicationRole.ADMIN.name()) || roles.contains(ApplicationRole.MANAGER.name())) {
            entityModel.add(
                    WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(BillController.class).updateBill(entity.getId(), null, authentication)).withRel("Update bill").withType("PUT"),
                    WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(BillController.class).deleteBill(entity.getId())).withRel("Delete bill").withType("DELETE"),
                    WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(BillController.class).createBill(null, authentication)).withRel("Create bill").withType("POST")
            );
        }

        return entityModel;
    }

    public <T extends BaseResponse> CollectionModel<EntityModel<T>> toCollectionModel(Iterable<T> entities, Authentication authentication) {
        return StreamSupport.stream(entities.spliterator(), false) //
                .map(t -> toModel(t, authentication)) //
                .collect(Collectors.collectingAndThen(Collectors.toList(), CollectionModel::of))
                //.add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(ReservationController.class)).withSelfRel().withType("GET"))
                ;
    }
}
