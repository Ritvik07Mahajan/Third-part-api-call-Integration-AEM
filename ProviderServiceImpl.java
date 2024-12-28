package et.com.aem.core.services;

import com.google.gson.Gson;
import et.com.aem.core.api.MedicalProviders;
import et.com.aem.core.config.ProviderServiceConfig;
import et.com.aem.core.models.Products;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Modified;
import org.osgi.service.metatype.annotations.Designate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.List;

@Component(service = ProviderService.class, immediate = true)
@Designate(ocd = ProviderServiceConfig.class)
public class ProviderServiceImpl implements ProviderService {

    private static final Logger LOG = LoggerFactory.getLogger(ProviderServiceImpl.class);

    private String providerApiUrl;
    private List<Products> products;

    @Activate
    @Modified
    protected void activate(ProviderServiceConfig config) {
        this.providerApiUrl = config.apiUrl();
    }

    @Override
    public String getProviderData() {
        LOG.info("The provided url is {}", providerApiUrl);
        String jsonData = "";
        try (CloseableHttpClient httpClient = HttpClients.createDefault()) {
            HttpGet request = new HttpGet(providerApiUrl);
            try (CloseableHttpResponse response = httpClient.execute(request)) {
                HttpEntity entity = response.getEntity();
                if (entity != null) {
                    jsonData = EntityUtils.toString(entity);
                }
            }
        } catch (IOException e) {
            LOG.error("Error fetching data from URL: {}", providerApiUrl, e);
        }

       return jsonData;
    }
}
