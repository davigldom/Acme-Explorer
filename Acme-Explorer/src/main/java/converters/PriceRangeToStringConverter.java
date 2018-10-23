
package converters;

import java.net.URLEncoder;

import javax.transaction.Transactional;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import domain.PriceRange;

@Component
@Transactional
public class PriceRangeToStringConverter implements Converter<PriceRange, String> {

	@Override
	public String convert(final PriceRange priceRange) {
		String result;
		StringBuilder builder;

		if (priceRange == null)
			result = null;
		else
			try {
				builder = new StringBuilder();
				builder.append(URLEncoder.encode(Double.toString(priceRange.getMinPrice()), "UTF-8"));
				builder.append("|");
				builder.append(URLEncoder.encode(Double.toString(priceRange.getMaxPrice()), "UTF-8"));
				result = builder.toString();
			} catch (final Throwable oops) {
				throw new RuntimeException(oops);
			}
		return result;
	}

}
