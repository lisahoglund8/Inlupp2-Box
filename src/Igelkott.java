class Igelkott{
    private String namn;
    private int taggar;
    private boolean ivrig;

    public Igelkott(String namn, int taggar, boolean ivrig){
	this.namn = namn;
	this.taggar = taggar;
	this.ivrig = ivrig;
    }

    public String getNamn(){ 
	return namn; 
    }
    public int getTaggar(){ 
	return taggar; 
    }

    public boolean isIvrig(){
	return ivrig;
    }

    public String toString(){
	String str = namn + " har " + taggar + " taggar";
	if (ivrig)
	    str += " är ivrig";
	return str;
   }

}
