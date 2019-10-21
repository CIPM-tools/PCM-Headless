package org.pcm.headless.core.transform;

import org.eclipse.emf.common.util.URI;

public interface IURITransformer {
	public URI transform(URI uri);

	public void installRule(URI uri);
}