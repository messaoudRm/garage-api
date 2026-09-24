package garage.app.facturation;

public enum TypePiece {
    STANDARD(12),
    EMBRAYAGE(24);

    private final int dureeGarantieMois;

    TypePiece(int dureeGarantieMois) {
        this.dureeGarantieMois = dureeGarantieMois;
    }

    public int dureeGarantieMois() {
        return dureeGarantieMois;
    }
}
