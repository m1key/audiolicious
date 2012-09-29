package me.m1key.audiolicious.services;

import java.io.File;
import java.util.concurrent.Future;

import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

import me.m1key.audiolicious.domain.to.LibraryTo;

@Stateless
@Local({ LibraryImporter.class, SynchronousLibraryImporter.class })
public class SynchronousCacheAwareLibraryImporter extends
		CacheAwareLibraryImporter implements SynchronousLibraryImporter {

	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public Future<LibraryTo> importLibrary(File libraryFile) {
		return super.importLibrary(libraryFile);
	}

}
