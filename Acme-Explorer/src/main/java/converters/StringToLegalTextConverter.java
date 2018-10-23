
package converters;

import javax.transaction.Transactional;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import repositories.LegalTextRepository;
import domain.LegalText;

@Component
@Transactional
public class StringToLegalTextConverter implements Converter<String, LegalText> {

	@Autowired
	LegalTextRepository	legalTextRepository;


	@Override
	public LegalText convert(final String text) {
		LegalText result;
		int id;

		try {
			if (StringUtils.isEmpty(text))
				result = null;
			else {
				id = Integer.valueOf(text);
				result = this.legalTextRepository.findOne(id);
			}
		} catch (final Throwable oops) {
			throw new IllegalArgumentException(oops);
		}

		return result;
	}

}
