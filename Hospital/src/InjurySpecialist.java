public abstract class InjurySpecialist extends SystemWorker {
    private final String infoQueueName;
    final static String INFO_HEADER = "INFO: ";

    InjurySpecialist() throws Exception {
        super();
        this.infoQueueName = this.channel.queueDeclare().getQueue();
        channel.queueBind(this.infoQueueName, INFO_EXCHANGE, "");
        registerConsumer(infoQueueName, INFO_HEADER, false, "");
    }

    String getRequetRoutingKey(String injury) {
        return REQUEST_BINDING_KEY.replace(".", injury);
    }

    String getReplyRoutingKey(String injury) {
        return REPLY_BINDING_KEY.replace(".", injury);
    }
}
