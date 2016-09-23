package de.ugoe.cs.cpdp.loader;

/**
 * Implements the {@link AbstractFolderLoader} for the NASA/SOFTLAB/MDP data
 * set.
 * 
 * @author Steffen Herbold
 */
public class NasaARFFFolderLoader extends AbstractFolderLoader {

	/*
	 * (non-Javadoc)
	 * 
	 * @see de.ugoe.cs.cpdp.loader.AbstractFolderLoader#getSingleLoader()
	 */
	@Override
	protected SingleVersionLoader getSingleLoader() {
		return new NasaARFFLoader();
	}

}
