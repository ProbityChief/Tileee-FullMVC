package dawan.projet.tileee.tool;

import java.nio.charset.StandardCharsets;

public class StringFunctions {

	 /**
     * Met le premier caract�re en majuscule
     * @param string
     * La cha�ne d'entr�e
     * @return
     * Retourne la cha�ne string apr�s avoir remplac� le premier caract�re par sa majuscule, si le premier caract�re est alphab�tique.
     */
    public static String ucFirst(String string){
        if(!string.equals(""))
            return string.substring(0, 1).toUpperCase()+ string.substring(1).toLowerCase();
        else
            return "";
    }
    
    /**
	 * Convertit un texte en ISO-8859-1 en UTF-8
	 * @param content
	 * Le texte en ISO-8859-1
	 * @return
	 * Le texte en UTF-8
	 */
	public static String utf8Encode(String content) {
		// getBytes contient un tableau d'octets qui contient le contenu de la cha�ne de caract�res encod� selon un encodage particulier
		byte[] bytes = content.getBytes(StandardCharsets.ISO_8859_1); // getBytes utilise par d�faut l'encodage de la machine qui contient la JVM
		// Un des constructeurs de l'objet String.
		return new String(bytes, StandardCharsets.UTF_8);
	}
    
}
