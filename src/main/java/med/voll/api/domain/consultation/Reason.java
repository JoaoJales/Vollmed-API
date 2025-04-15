package med.voll.api.domain.consultation;

public enum Reason {
    PACIENTE_DESISTIU("Paciente desistiu"),
    MEDICO_CANCELOU("Médico cancelou"),
    OUTROS("Outros");

    private String reasonCancel;

    Reason(String reasonCancel){
        this.reasonCancel = reasonCancel;
    }

    public static Reason fromString(String textReason){
        for(Reason reason : Reason.values()){
            if (reason.reasonCancel.equalsIgnoreCase(textReason)){
                return reason;
            }
        }
        throw new IllegalArgumentException("Razão de cancelamento iválida!");
    }
}
