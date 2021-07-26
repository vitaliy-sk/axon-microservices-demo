package com.epam.fullfilment.event;

public class ShippingArrivedEvent {

    private String orderId;
    private String shipmentId;

    public ShippingArrivedEvent(String orderId, String shipmentId) {
        this.orderId = orderId;
        this.shipmentId = shipmentId;
    }

    public String getOrderId() {
        return orderId;
    }

    public String getShipmentId() {
        return shipmentId;
    }
}
