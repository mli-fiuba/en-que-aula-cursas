package com.mlifiuba.dondecurso.api;

import java.io.Serializable;
import java.util.List;

public interface InformationStrategy extends Serializable {

	List<InformationModel> getInfo(String selection);

	String getLabel();
}
