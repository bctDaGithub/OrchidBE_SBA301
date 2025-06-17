package org.example.orchidbe.command.services.define;

import org.example.orchidbe.command.entities.OrchidEntity;

public interface IOrchidCommandService {
    public void createOrchid(OrchidEntity orchidEntity);
    public void updateOrchid(Long orchidId, String orchidName, String orchidDescription, double price, String orchidUrl, boolean isNatural);
    public void disableOrchid(Long orchidId);
    public void enableOrchid(Long orchidId);
}
