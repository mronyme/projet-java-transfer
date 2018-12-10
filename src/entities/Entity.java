package entities;

import enums.EntityEnum;

public abstract class Entity {
	EntityEnum type;
	public Entity(EntityEnum type)
	{
		this.type = type;
	}
	public EntityEnum getType()
	{
		return type;
	}
}
