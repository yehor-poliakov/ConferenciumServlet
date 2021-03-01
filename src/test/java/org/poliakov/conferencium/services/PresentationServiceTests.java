package org.poliakov.conferencium.services;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.poliakov.conferencium.dao.presentation.PresentationDao;
import org.poliakov.conferencium.model.presentation.Presentation;
import org.poliakov.conferencium.model.presentation.PresentationBuilder;
import org.poliakov.conferencium.model.presentation.PresentationDetails;
import org.poliakov.conferencium.model.presentation.PresentationDetailsBuilder;
import org.poliakov.conferencium.model.user.User;
import org.poliakov.conferencium.service.presentation.PresentationService;
import org.poliakov.conferencium.service.presentation.PresentationServiceImpl;
import org.poliakov.conferencium.util.Page;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class PresentationServiceTests {
    @Mock
    private PresentationDao presentationDao;
    private PresentationService presentationService;

    @Before
    public void init() {
        presentationService = new PresentationServiceImpl(presentationDao);
    }

    @Test
    public void shouldCreatePresentation() {
        // arrange
        Presentation presentation = mock(Presentation.class);
        when(presentationDao.createPresentation(presentation)).thenReturn(presentation);

        // act
        Presentation actual = presentationService.createPresentation(presentation);

        // assert
        verify(presentationDao, only()).createPresentation(any());
        Assert.assertEquals(presentation, actual);
    }

    @Test
    public void shouldSetSpeakerApprovedToFalseIfSpeakerIdIsNull() {
        // arrange
        Presentation presentation = mock(Presentation.class);
        presentation.setSpeakerId(null);
        presentation.setSpeakerApproved(true);
        when(presentationDao.createPresentation(presentation)).thenReturn(presentation);

        // act
        Presentation actual = presentationService.createPresentation(presentation);

        // assert
        verify(presentationDao, only()).createPresentation(any());
        Assert.assertEquals(presentation, actual);
        Assert.assertFalse(presentation.isSpeakerApproved());
    }

    @Test
    public void shouldFindPresentationById() {
        // arrange
        Presentation presentation = new PresentationBuilder()
                .setId(123L)
                .build();
        Long presentationId = 123L;
        when(presentationDao.findPresentationById(presentation.getId())).thenReturn(presentation);

        // act
        Presentation actual = presentationService.findPresentationById(presentationId);

        // assert
        verify(presentationDao, only()).findPresentationById(any());
        Assert.assertEquals(presentation, actual);
    }

    @Test
    public void shouldReturnNullNotFindingPresentationById() {
        // arrange
        Presentation presentation = mock(Presentation.class);
        Long presentationId = 123L;
        Long wrongPresetnationId = 124L;
        presentation.setId(presentationId);

        when(presentationDao.findPresentationById(wrongPresetnationId)).thenReturn(null);

        // act
        Presentation actual = presentationService.findPresentationById(wrongPresetnationId);

        // assert
        verify(presentationDao, only()).findPresentationById(any());
        Assert.assertNull(actual);
    }

    @Test
    public void shouldFindAllByConferenceId() {
        List<Presentation> presentations = new ArrayList<Presentation>();
        Long conferenceId = 1L;
        presentations.add(new PresentationBuilder().setId(1L).setConferenceId(conferenceId).build());
        presentations.add(new PresentationBuilder().setId(2L).setConferenceId(conferenceId).build());

        when(presentationDao.findAllByConferenceId(conferenceId)).thenReturn(presentations);

        // act
        List<Presentation> actual = presentationService.findAllByConferenceId(conferenceId);

        // assert
        verify(presentationDao, only()).findAllByConferenceId(any());
        Assert.assertEquals(presentations.toArray().length, actual.toArray().length);
        presentations.forEach(p -> Assert.assertTrue(actual.contains(p)));
    }

    @Test
    public void shouldFindAllBySpeakerId() {
        List<PresentationDetails> presentations = new ArrayList<PresentationDetails>();
        Long speakerId = 1L;
        presentations.add(new PresentationDetailsBuilder().build());
        presentations.add(new PresentationDetailsBuilder().build());
        int pageNumber = 1;
        int pageSize = 10;
        int pageTotal = 42;
        when(presentationDao.findAllBySpeakerId(speakerId, pageNumber, pageSize)).thenReturn(presentations);
        when(presentationDao.countBySpeaker(speakerId)).thenReturn(pageTotal);

        // act
        Page<PresentationDetails> actual = presentationService.findAllBySpeakerId(speakerId, pageNumber, pageSize);

        // assert
        verify(presentationDao, times(1)).findAllBySpeakerId(any(), any(), any());
        verify(presentationDao, times(1)).countBySpeaker(any());

        Assert.assertEquals(Math.ceil((double)pageTotal/pageSize), (double)actual.getTotalPages(), 0.01);
        Assert.assertEquals(presentations.toArray().length, actual.getItems().size());
        presentations.stream().forEach(p -> Assert.assertTrue(actual.getItems().contains(p)));
    }


}

