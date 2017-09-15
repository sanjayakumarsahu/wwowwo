

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.fasterxml.jackson.databind.ObjectMapper;

import com.geaviation.eds.service.events.ws.impl.EventsShopInfoServicesImpl;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = EventsServicesSBApp.class)
public class EventsServicesTest {

	@InjectMocks
	EventsShopInfoServicesImpl eventsInfoServiceImpl;

	@InjectMocks
	EventsMaintenanceServicesImpl eventsMaintenanceServicesImpl;

	@InjectMocks
	EventsForecastRemovalServicesImpl eventsForecastServiceImpl;


	@Autowired
	WebApplicationContext context;

	private MockMvc mvc;

	@Before
	public void initTests() {
		MockitoAnnotations.initMocks(this);
		mvc = MockMvcBuilders.webAppContextSetup(context).build();
	}

	
	@Test
	public void getShopvisitOverhaulData() throws DataServiceException {
		try{
				MaintenanceReq maintenanceReq=new MaintenanceReq();
				List<String> esnList=new ArrayList<>();
				esnList.add("550485");
				esnList.add("994278");
				esnList.add("733190");
				maintenanceReq.setEsn(esnList);
				maintenanceReq.setEventStartDate("15-01-2016");
				maintenanceReq.setEventEndDate("26-01-2017");
				mvc.perform(post("/v1/maintenance/shopvisitoverhaul")
						.content(asJsonString(maintenanceReq))
						.header(DataServiceConstants.CONSUMER_APP,DataServiceConstants.JUNIT_TEST)
						.contentType(MediaType.APPLICATION_JSON)
						.accept(MediaType.APPLICATION_JSON))
						.andExpect(status().isOk()).andReturn();
			
		} catch (Exception e) {

			throw new DataServiceException("Junit Failed v1/maintenance/shopvisitoverhaul",e);
		}
	}
	 	
	public static String asJsonString(final Object obj)
			throws DataServiceException {
		try {
			final ObjectMapper mapper = new ObjectMapper();
			return mapper.writeValueAsString(obj);
		} catch (Exception e) {
			throw new DataServiceException("Json to String convertion error", e);
		}
	}
}