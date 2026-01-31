/**
 * This file is part of the Sandy Andryanto Online Store Website.
 *
 * @author     Sandy Andryanto <sandy.andryanto.official@gmail.com>
 * @copyright  2025
 *
 * For the full copyright and license information,
 * please view the LICENSE.md file that was distributed
 * with this source code.
 */

package com.api.backend.models.services.implementations;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.api.backend.models.dto.ActivityListDto;
import com.api.backend.models.entities.Activity;
import com.api.backend.models.entities.User;
import com.api.backend.models.repositories.ActivityRepository;
import com.api.backend.models.services.interfaces.IActivityService;

import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;

import com.api.backend.helpers.Helper;

@Service
public class ActivityService implements IActivityService {

	@Autowired
	private ActivityRepository repo;

	@Autowired
	private EntityManager entityManager;

	@Override
	public void saveActivity(User user, String Subject, String Event, String Description) {
		try {
			Activity activity = new Activity();
			activity.setSubject(Subject);
			activity.setUser(user);
			activity.setEvent(Event);
			activity.setStatus(1);
			activity.setDescription(Description);
			activity.setCreatedAt(Helper.DateNow());
			activity.setUpdatedAt(Helper.DateNow());
			this.repo.save(activity);
		} catch (ParseException e) {
			e.printStackTrace();
		}

	}

	@Override
	public HashMap<String, Object> getByUser(long user_id, int page, int limit, String order, String sort,
			String search) {
		// TODO Auto-generated method stub

		HashMap<String, Object> payload = new HashMap<>();
		Long totalAll = repo.TotalAll(user_id);
		Long totalFiltered = repo.TotalAll(user_id);
		int offset = ((page - 1) * limit);

		String Additional = " x.id IS NOT NULL";

		String base = """
				SELECT
					*
				FROM public.activities x
				WHERE x.user_id = :user_id
				AND (
				    LOWER(CAST(x.event as varchar)) LIKE :search
				    OR LOWER(CAST(x.subject as varchar)) LIKE :search
				    OR LOWER(CAST(x.description as varchar)) LIKE :search
				)
				AND """ + Additional + """
				    ORDER BY %s %s
				    LIMIT :limit OFFSET :offset
				""".formatted(order, sort);

		Query query = entityManager.createNativeQuery(base, Activity.class);
		query.setParameter("user_id", user_id);
		query.setParameter("limit", limit);
		query.setParameter("offset", offset);
		query.setParameter("search", "%" + search.toLowerCase() + "%");
		List<Activity> getData = query.getResultList();
		totalFiltered = (long) getData.size();

		List<ActivityListDto> dd = new ArrayList<>();

		for (Activity row : getData) {
			ActivityListDto ac = new ActivityListDto();
			ac.setSubject(row.getSubject());
			ac.setEvent(row.getEvent());
			ac.setDescription(row.getDescription());
			ac.setCreatedAt(row.getCreatedAt());
			dd.add(ac);
		}

		payload.put("list", dd);
		payload.put("totalAll", totalAll);
		payload.put("totalFiltered", totalFiltered);
		payload.put("limit", limit);
		payload.put("page", page);
		return payload;
	}

	@Override
	public long TotalAll(long user_id) {
		// TODO Auto-generated method stub
		return repo.TotalAll(user_id);
	}

}
