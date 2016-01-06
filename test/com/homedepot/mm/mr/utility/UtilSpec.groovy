package com.homedepot.mm.mr.utility

import spock.lang.Specification

class UtilSpec extends Specification {
	
	def "Round a number to currency format with 2 decimal places" () {
		
		given:
			def util = new Util()
			def amount = '444'
			
		when: "Rounds of the amount to two decimals "
			def rounded = util.roundOfTwoDecimalPlaces(amount)	
			
		then:
		   rounded.equals('$444.00')		
	}
}

