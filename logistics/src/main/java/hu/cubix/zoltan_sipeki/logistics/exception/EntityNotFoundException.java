package hu.cubix.zoltan_sipeki.logistics.exception;

public class EntityNotFoundException extends Exception {
    public EntityNotFoundException(String entity, long entityId) {
        super(String.format("'%s' with id '%d' does not exist.", entity, entityId));
    }
}
