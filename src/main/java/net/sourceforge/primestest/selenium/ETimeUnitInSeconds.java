package net.sourceforge.primestest.selenium;

public enum ETimeUnitInSeconds {
	
	CINCO_SEGUNDOS(5),
	QUINZE_SEGUNDOS(15),
	UM_MINUTO(60),
	TRES_MINUTOS(180),
	QUATRO_MINUTOS(240),
	CINCO_MINUTOS(300);
	
	private final long time;
	
	private ETimeUnitInSeconds(final long time) {
		this.time = time;
	}
	
	public long getTime() {
		return time;
	}
}
