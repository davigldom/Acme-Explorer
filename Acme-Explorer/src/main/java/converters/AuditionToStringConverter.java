
package converters;

import javax.transaction.Transactional;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import domain.Audition;

@Component
@Transactional
public class AuditionToStringConverter implements Converter<Audition, String> {

	@Override
	public String convert(final Audition audition) {
		String result;

		if (audition== null)
			result = null;
		else
			result = String.valueOf(audition.getId());

		return result;
	}

}
