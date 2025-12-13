import java.io.PrintStream;
import java.util.Comparator;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

public class Exercise3 {
    public static void main(String[] args){
     CountryDao countryDao = InMemoryWorldDao.getInstance();
    CityDao cityDao = InMemoryWorldDao.getInstance();
// The Highest Populated Capital city fozr each content
Map<String, Optional<City>> highestCapitalByContinent =
        countryDao.findAllCountries().stream()
                .collect(Collectors.groupingBy(
                        Country::getContinent,
                        Collectors.collectingAndThen(Collectors.toList(),
                                list -> list.stream()
                                        .map(c -> cityDao.findCityById(c.getCapital())) // capitals only
                                        .filter(Objects::nonNull)
                                        .max(Comparator.comparingInt(City::getPopulation))
                        )
                ));

highestCapitalByContinent.forEach((continent, optCity) -> {
    if (optCity.isPresent()) {
        City c = optCity.get();
        System.out.println(continent + " → " + c.getName() + " (" + c.getPopulation() + ")");
    } else {
        System.out.println(continent + " → No capital cities");
    }
});
             

    }
  

}
