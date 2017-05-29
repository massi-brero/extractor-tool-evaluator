package de.mbrero.see.writer;

/**
 * Very generic interface (pun intended) to save entities like Annotations and the like.
 * @author massi.brero@gmail.com
 *
 * @param <T>
 */
public interface IAnnotationWriter<T> {
	
	public void save(T annotations);

}
