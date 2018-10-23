
package converters;

import java.net.URLEncoder;

import javax.transaction.Transactional;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import domain.Period;

@Component
@Transactional
public class PeriodToStringConverter implements Converter<Period, String> {

	@Override
	public String convert(final Period period) {
		String result;
		StringBuilder builder;

		if (period == null)
			result = null;
		else
			try {
				builder = new StringBuilder();
				builder.append(URLEncoder.encode(period.getStart().toString(), "UTF-8"));
				builder.append("|");
				builder.append(URLEncoder.encode(period.getEnd().toString(), "UTF-8"));
				result = builder.toString();
			} catch (final Throwable oops) {
				throw new RuntimeException(oops);
			}
		return result;
	}

}
