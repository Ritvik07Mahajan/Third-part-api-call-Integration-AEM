package et.com.aem.core.models;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import et.com.aem.core.api.MedicalProvider;
import et.com.aem.core.api.MedicalProviders;
import et.com.aem.core.services.ProviderService;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.ValueMapValue;
import org.apache.sling.models.annotations.injectorspecific.OSGiService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.PostConstruct;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

@Model(adaptables = Resource.class, defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
public class ProviderModel {

    private static final Logger LOG = LoggerFactory.getLogger(ProviderModel.class);

    @OSGiService
    private ProviderService providerService;

    private List<Products> products;

    public List<Products> getProducts() {
        return products;
    }


    @PostConstruct
    protected void init() {

        String jsonData= providerService.getProviderData();
        Gson gson = new Gson();
        Type listType = new TypeToken<ArrayList<Products>>() {}.getType();
        products = gson.fromJson(jsonData, listType);
    }

}

