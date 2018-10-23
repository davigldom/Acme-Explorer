
package converters;

import java.net.URLDecoder;

import javax.transaction.Transactional;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import domain.PriceRange;

@Component
@Transactional
public class StringToPriceRangeConverter implements Converter<String, PriceRange> {

	@Override
	public PriceRange convert(final String text) {
		PriceRange result;
		String parts[];

		if (text == null)
			result = null;
		else
			try {
				parts = text.split("\\|");
				result = new PriceRange();
				result.setMinPrice(Double.valueOf(URLDecoder.decode(parts[0], "UTF-8")));
				result.setMaxPrice(Double.valueOf(URLDecoder.decode(parts[1], "UTF-8")));

			} catch (final Throwable oops) {
				throw new IllegalArgumentException(oops);
			}

		return result;
	}

}
