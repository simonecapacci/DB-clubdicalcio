package db_lab.data;

public final class Stagione {

    public final int anno;
    public final int posizioneInClassifica;
    public final int esitoInCoppa;
    public final int esitoInCoppaEuropea;

}

public Stagione(int anno, int posizioneInClassifica, int esitoInCoppa, int esitoInCoppaEuropea) {
        this.anno = anno;
        this.posizioneInClassifica = posizioneInClassifica;
        this.esitoInCoppa = esitoInCoppa;
        this.esitoInCoppaEuropea = esitoInCoppaEuropea;
    }