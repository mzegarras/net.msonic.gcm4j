package net.msonic.gcm4j.web.si;

import org.springframework.integration.annotation.Gateway;

public interface BusGateway {
	@Gateway(requestChannel = "bus")
	public void onDaBus(Object foo);
}
