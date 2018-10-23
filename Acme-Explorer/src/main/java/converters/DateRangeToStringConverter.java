
package converters;

import java.net.URLEncoder;

import javax.transaction.Transactional;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import domain.DateRange;

@Component
@Transactional
public class DateRangeToStringConverter implements Converter<DateRange, String> {

	@Override
	public String convert(final DateRange dateRange) {
		String result;
		StringBuilder builder;

		if (dateRange == null)
			result = null;
		else{
			try{
				builder = new StringBuilder();
				builder.append(URLEncoder.encode(dateRange.getStart().toString(), "UTF-8"));
				builder.append("|");
				builder.append(URLEncoder.encode(dateRange.getEnd().toString(), "UTF-8"));
				result = builder.toString();
			}catch(final Throwable oops){
				throw new RuntimeException(oops);
			}
		}
		return result;
	}

}
