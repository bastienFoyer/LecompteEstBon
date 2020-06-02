package model;

/**
 * Factory du model
 */
public class SingletonFactoryModel {
	
	/** Singleton */
	private static SingletonFactoryModel factoryModelSingleton = new SingletonFactoryModel();
	
	/** Model de la partie */
	private static Model model;
	
	/**
	 * Ajoute un model à l'instance
	 * @param dureeMaxi temps max
	 */
	public static void addInstance(int dureeMaxi){
			model = new Model(dureeMaxi);
	}

	public static Model getInstance(){
		return model;
	}

	public static SingletonFactoryModel getUniqueModel() {
		return factoryModelSingleton;
	}

	public static void setUniqueModel(SingletonFactoryModel uniqueModel) {
		SingletonFactoryModel.factoryModelSingleton = uniqueModel;
	}

}

