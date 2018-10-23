
package converters;

import java.net.URLDecoder;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.transaction.Transactional;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import domain.Period;

@Component
@Transactional
public class StringToPeriodConverter implements Converter<String, Period> {

	@Override
	public Period convert(final String text) {
		Period result;
		String parts[];

		if (text == null)
			result = null;
		else
			try {
				parts = text.split("\\|");
				result = new Period();
				result.setStart(this.parseDate(URLDecoder.decode(parts[0], "UTF-8")));
				result.setEnd(this.parseDate(URLDecoder.decode(parts[1], "UTF-8")));

			} catch (final Throwable oops) {
				throw new IllegalArgumentException(oops);
			}

		return result;
	}

	private Date parseDate(final String date) {
		try {
			return new SimpleDateFormat("dd-MM-yyyy").parse(date);
		} catch (final ParseException e) {
			return null;
		}
	}

}
