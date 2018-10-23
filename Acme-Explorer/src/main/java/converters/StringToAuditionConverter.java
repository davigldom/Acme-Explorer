
package converters;

import javax.transaction.Transactional;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import repositories.AuditionRepository;
import domain.Audition;

@Component
@Transactional
public class StringToAuditionConverter implements Converter<String, Audition> {

	@Autowired
	AuditionRepository	auditionRepository;


	@Override
	public Audition convert(final String text) {
		Audition result;
		int id;

		try {
			if (StringUtils.isEmpty(text))
				result = null;
			else {
				id = Integer.valueOf(text);
				result = this.auditionRepository.findOne(id);
			}
		} catch (final Throwable oops) {
			throw new IllegalArgumentException(oops);
		}

		return result;
	}

}
