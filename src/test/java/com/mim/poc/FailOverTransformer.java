package com.mim.poc;

import com.github.tomakehurst.wiremock.common.FileSource;
import com.github.tomakehurst.wiremock.extension.Parameters;
import com.github.tomakehurst.wiremock.extension.ResponseDefinitionTransformer;
import com.github.tomakehurst.wiremock.http.Request;
import com.github.tomakehurst.wiremock.http.ResponseDefinition;

public class FailOverTransformer extends ResponseDefinitionTransformer {

	@Override
	public String getName() {
		
		return "fail-over-transformer";
	}

	@Override
	public ResponseDefinition transform(Request request, ResponseDefinition responseDefinition, FileSource files,
			Parameters parameters) {
		return null;
	}

}
