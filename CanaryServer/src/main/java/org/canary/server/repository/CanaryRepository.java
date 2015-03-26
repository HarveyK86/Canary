package org.canary.server.repository;

import org.canary.server.model.Canary;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;

@Repository
public final class CanaryRepository extends AbstractRepository<Canary>
{
	private CanaryRepository()
	{
		super();
	}

	@Override
	public Canary create(final String message)
	{
		final Canary canary = new Canary();
		final Session session = super.getSession();
		final int id;

		canary.setMessage(message);
		id = (int) session.save(canary);

		return super.read(id);
	}

	@Override
	public Class<Canary> getModelClass()
	{
		return Canary.class;
	}

}
