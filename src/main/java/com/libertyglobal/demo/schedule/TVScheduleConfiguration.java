package com.libertyglobal.demo.schedule;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.dropwizard.Configuration;
import io.dropwizard.client.JerseyClientConfiguration;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

public class TVScheduleConfiguration extends Configuration
{
	@NotEmpty
	final private String template;

	@NotEmpty
	final private String defaultName;

	@JsonCreator
	public TVScheduleConfiguration(@JsonProperty("template") String template, @JsonProperty("defaultName") String defaultName)
	{
		this.template = template;
		this.defaultName = defaultName;
	}

	@JsonProperty
	public String getTemplate()
	{
		return template;
	}

	@JsonProperty
	public String getDefaultName()
	{
		return defaultName;
	}

	@Valid
	@NotNull
	private JerseyClientConfiguration jerseyClient = new JerseyClientConfiguration();

	@JsonProperty("jerseyClient")
	public JerseyClientConfiguration getJerseyClientConfiguration() {
		return jerseyClient;
	}

	@JsonProperty("jerseyClient")
	public void setJerseyClientConfiguration(JerseyClientConfiguration jerseyClient) {
		this.jerseyClient = jerseyClient;
	}
}
